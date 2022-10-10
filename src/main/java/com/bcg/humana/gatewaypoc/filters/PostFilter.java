//package com.bcg.humana.gatewaypoc.filters;
//
//import com.bcg.humana.gatewaypoc.decorators.PostFilterBodyCapture;
//import java.nio.charset.StandardCharsets;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.annotation.Order;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.core.io.buffer.DataBufferFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.reactive.ServerHttpResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Slf4j
//@Component
//@Order(1)
//public class PostFilter implements GlobalFilter {
//
//  @Override
//  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//      ServerHttpResponse response = exchange.getResponse();
//      DataBufferFactory dataBufferFactory = response.bufferFactory();
//      HttpStatus responseStatus = response.getStatusCode();
//      log.info("post filter executed");
//      if (responseStatus != null && responseStatus.equals(HttpStatus.BAD_REQUEST)) {
//        String newResponseBody =
//            "<body>\n" +
//                "      <h1 style=\"color:red;text-align:center\">Bad Request </h1>\n" +
//                "      <p>If you are seeing this page it means response body is modified.</p>\n" +
//                "  </body>";
//
//        DataBuffer dataBuffer = response.bufferFactory().wrap(newResponseBody.getBytes(
//            StandardCharsets.UTF_8));
//        response.writeWith(Mono.just(dataBuffer));
//        exchange.mutate().response(response).build();
//      } else {
//        PostFilterBodyCapture decoratedResponse = new PostFilterBodyCapture(response,
//            dataBufferFactory);
//        exchange.mutate().response(decoratedResponse).build();
//      }
//    }));
//
//  }
//}
