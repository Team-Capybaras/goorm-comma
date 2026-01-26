package groom.backend.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import groom.backend.application.avoidance.dto.response.CongestionRecommendResponse;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
@EnableCaching
public class RedisCacheConfig {


  @Bean
  public RedisCacheManager redisCacheManager(
          RedisConnectionFactory connectionFactory
  ) {

    // Redis 전용 ObjectMapper (전역과 완전히 분리)
//    ObjectMapper redisObjectMapper = new ObjectMapper();
//
//    redisObjectMapper.registerModule(new JavaTimeModule());
//    redisObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    GenericJackson2JsonRedisSerializer serializer =
            new GenericJackson2JsonRedisSerializer(redisObjectMapper());

    RedisCacheConfiguration cacheConfig =
            RedisCacheConfiguration.defaultCacheConfig()
                    .serializeValuesWith(
                            RedisSerializationContext.SerializationPair
                                    .fromSerializer(serializer)
                    );

    return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(cacheConfig)
            .build();
  }

  @Bean(name = "redisObjectMapper")
  public ObjectMapper redisObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }

  @Bean
  public GenericJackson2JsonRedisSerializer redisValueSerializer(
          ObjectMapper redisObjectMapper
  ) {
    return new GenericJackson2JsonRedisSerializer(redisObjectMapper);
  }

  @Bean
  public Jackson2JsonRedisSerializer<CongestionRecommendResponse>
  avoidanceValueSerializer(
          ObjectMapper avoidanceRedisObjectMapper
  ) {
    Jackson2JsonRedisSerializer<CongestionRecommendResponse> serializer =
            new Jackson2JsonRedisSerializer<>(avoidanceRedisObjectMapper, CongestionRecommendResponse.class);
    return serializer;
  }

  @Bean
  public CacheManager avoidanceCacheManager(
          RedisConnectionFactory connectionFactory,
          Jackson2JsonRedisSerializer<CongestionRecommendResponse> avoidanceValueSerializer
  ) {

    RedisCacheConfiguration cacheConfig =
            RedisCacheConfiguration.defaultCacheConfig()
                    .serializeValuesWith(
                            RedisSerializationContext.SerializationPair
                                    .fromSerializer(avoidanceValueSerializer)
                    )
                    .entryTtl(Duration.ofMinutes(60));

    return RedisCacheManager.builder(connectionFactory)
            .cacheDefaults(cacheConfig)
            .build();
  }

}

