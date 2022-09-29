package com.bcg.humana.gatewaypoc.cache;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {

  private final RedisProperties redisProperties;

  public RedisConfig(RedisProperties redisProperties) {
    this.redisProperties = redisProperties;
  }

  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory() {
    return new LettuceConnectionFactory(this.redisProperties.getHost(),
        this.redisProperties.getPort());
  }

  @Bean
  @Primary
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(this.lettuceConnectionFactory());
    return template;
  }

//  @Bean
//  public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
//    return new LettuceConnectionFactory(this.redisProperties.getHost(),
//        this.redisProperties.getPort());
//  }
//
//  @Bean
//  public ReactiveRedisTemplate<String, Object> redisTemplate(
//      ReactiveRedisConnectionFactory factory) {
//    StringRedisSerializer keySerializer = new StringRedisSerializer();
//    Jackson2JsonRedisSerializer<Object> valueSerializer =
//        new Jackson2JsonRedisSerializer<>(Object.class);
//    RedisSerializationContext.RedisSerializationContextBuilder<String, Object> builder =
//        RedisSerializationContext.newSerializationContext(keySerializer);
//    RedisSerializationContext<String, Object> context =
//        builder.value(valueSerializer).build();
//    return new ReactiveRedisTemplate<>(factory, context);
//  }
//
//  @Bean
//  public ReactiveValueOperations<String, Object> reactiveValueOps(
//      ReactiveRedisTemplate<String, Object> redisTemplate) {
//    return redisTemplate.opsForValue();
//  }
}
