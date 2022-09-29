package com.bcg.humana.gatewaypoc.filters;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Order(-1)
public class CacheFilter implements WebFilter {

  RedisTemplate<String, Object> redisTemplate;

  public CacheFilter(
      RedisTemplate<String, Object> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @NotNull
  @Override
  public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
    final var cachedRequest = getCachedRequest(exchange.getRequest());
    if (Boolean.TRUE.equals(redisTemplate.hasKey(cachedRequest.toString()))) {
      log.info("Return cached response for request: {}", cachedRequest);
      CachedResponse cachedResponse = (CachedResponse) redisTemplate.opsForValue()
          .get(cachedRequest.toString());
      final var serverHttpResponse = exchange.getResponse();
      serverHttpResponse.setStatusCode(cachedResponse.getHttpStatus());
      serverHttpResponse.getHeaders().addAll(cachedResponse.getHeaders());
      final var buffer = exchange.getResponse().bufferFactory().wrap(cachedResponse.getBody());
      return exchange.getResponse().writeWith(Flux.just(buffer));
    }

    ServerHttpResponse mutatedHttpResponse = getServerHttpResponse(exchange, cachedRequest);
    return chain.filter(exchange.mutate().response(mutatedHttpResponse).build());
  }

  private ServerHttpResponse getServerHttpResponse(ServerWebExchange exchange,
      CachedRequest cachedRequest) {
    final var originalResponse = exchange.getResponse();
    final var dataBufferFactory = originalResponse.bufferFactory();

    return new ServerHttpResponseDecorator(originalResponse) {

      @NonNull
      @Override
      public Mono<Void> writeWith(@NotNull Publisher<? extends DataBuffer> body) {
        if (body instanceof Flux) {
          final var flux = (Flux<? extends DataBuffer>) body;
          return super.writeWith(flux.buffer().map(dataBuffers -> {
            final var outputStream = new ByteArrayOutputStream();
            dataBuffers.forEach(dataBuffer -> {
              final var responseContent = new byte[dataBuffer.readableByteCount()];
              dataBuffer.read(responseContent);
              try {
                outputStream.write(responseContent);
              } catch (IOException e) {
                throw new RuntimeException("Error while reading response stream", e);
              }
            });
            if (getStatusCode().is2xxSuccessful()) {
              final var cachedResponse = new CachedResponse(getStatusCode(), getHeaders(),
                  outputStream.toByteArray());
              log.debug("Request {} Cached response {}", cachedRequest.toString(),
                  new String(cachedResponse.getBody(), UTF_8));
              redisTemplate.opsForValue().set(cachedRequest.toString(), cachedResponse);
            }
            return dataBufferFactory.wrap(outputStream.toByteArray());
          }));
        }
        return super.writeWith(body);
      }
    };
  }

  private CachedRequest getCachedRequest(ServerHttpRequest request) {
    return CachedRequest.builder()
        .method(request.getMethod())
        .path(request.getPath())
        .queryParams(request.getQueryParams())
        .build();
  }

  @Builder
  @Data
  @ToString
  private static class CachedRequest {

    RequestPath path;
    HttpMethod method;
    MultiValueMap<String, String> queryParams;
  }

  @Builder
  @ToString
  @Data
  private static class CachedResponse {

    HttpStatus httpStatus;
    HttpHeaders headers;
    byte[] body;
  }
}
