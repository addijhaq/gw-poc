//package com.bcg.humana.gatewaypoc.filters;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Component
//public class CacheFilter implements GlobalFilter, Ordered {
//
//  private final CacheManager cacheManager;
//
//  public CacheFilter(CacheManager cacheManager) {
//    this.cacheManager = cacheManager;
//  }
//
//  @Override
//  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//    final var cache = cacheManager.getCache("MyCache");
//
//    final var cachedRequest = getCachedRequest(exchange.getRequest());
//    if (nonNull(cache.get(cachedRequest))) {
//      log.info("Return cached response for request: {}", cachedRequest);
//      final var cachedResponse = cache.get(cachedRequest, CachedResponse.class);
//
//      final var serverHttpResponse = exchange.getResponse();
//      serverHttpResponse.setStatusCode(cachedResponse.httpStatus);
//      serverHttpResponse.getHeaders().addAll(cachedResponse.headers);
//      final var buffer = exchange.getResponse().bufferFactory().wrap(cachedResponse.body);
//      return exchange.getResponse().writeWith(Flux.just(buffer));
//    }
//
//    final var mutatedHttpResponse = getServerHttpResponse(exchange, cache, cachedRequest);
//    return chain.filter(exchange.mutate().response(mutatedHttpResponse).build());
//  }
//
//  private ServerHttpResponse getServerHttpResponse(ServerWebExchange exchange, Cache cache,
//      CachedRequest cachedRequest) {
//    final var originalResponse = exchange.getResponse();
//    final var dataBufferFactory = originalResponse.bufferFactory();
//
//    return new ServerHttpResponseDecorator(originalResponse) {
//
//      @NonNull
//      @Override
//      public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
//        if (body instanceof final Flux<? extends DataBuffer> flux) {
//          return super.writeWith(flux.buffer().map(dataBuffers -> {
//            final var outputStream = new ByteArrayOutputStream();
//            dataBuffers.forEach(dataBuffer -> {
//              final var responseContent = new byte[dataBuffer.readableByteCount()];
//              dataBuffer.read(responseContent);
//              try {
//                outputStream.write(responseContent);
//              } catch (IOException e) {
//                throw new RuntimeException("Error while reading response stream", e);
//              }
//            });
//            if (Objects.requireNonNull(getStatusCode()).is2xxSuccessful()) {
//              final var cachedResponse = new CachedResponse(getStatusCode(), getHeaders(),
//                  outputStream.toByteArray());
//              log.debug("Request {} Cached response {}", cacheKey.getPath(),
//                  new String(cachedResponse.getBody(), UTF_8));
//              cache.put(cacheKey, cachedResponse);
//            }
//            return dataBufferFactory.wrap(outputStream.toByteArray());
//          }));
//        }
//        return super.writeWith(body);
//      }
//    };
//  }
//
//  @Override
//  public int getOrder() {
//    return -2;
//  }
//
//  private CachedRequest getCachedRequest(ServerHttpRequest request) {
//    return CachedRequest.builder()
//        .method(request.getMethod())
//        .path(request.getPath())
//        .queryParams(request.getQueryParams())
//        .build();
//  }
//
//  @Value
//  @Builder
//  private static class CachedRequest {
//
//    RequestPath path;
//    HttpMethod method;
//    MultiValueMap<String, String> queryParams;
//
//  }
//
//  @Value
//  private static class CachedResponse {
//
//    HttpStatus httpStatus;
//    HttpHeaders headers;
//    byte[] body;
//  }
//}
