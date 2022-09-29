package com.bcg.humana.gatewaypoc.decorators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class PostFilterBodyCapture extends ServerHttpResponseDecorator {

  DataBufferFactory bufferFactory;

  public PostFilterBodyCapture(ServerHttpResponse delegate, DataBufferFactory bufferFactory) {
    super(delegate);
    this.bufferFactory = bufferFactory;
  }

  @NotNull
  @Override
  public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
    if (body instanceof Flux) {
      Flux<? extends DataBuffer> flux = (Flux<? extends DataBuffer>) body;
      return super.writeWith(flux.buffer().map(dataBuffers -> {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        dataBuffers.forEach(i -> {
          byte[] array = new byte[i.readableByteCount()];
          i.read(array);
          try {
            outputStream.write(array);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
        return bufferFactory.wrap(outputStream.toByteArray());
      }));
    } else {
      log.info("body is not flux");
    }
    return super.writeWith(body);
  }
}
