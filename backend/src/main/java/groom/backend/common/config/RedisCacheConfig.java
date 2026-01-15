package groom.backend.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class RedisCacheConfig {


  @Bean
  public RedisCacheManager redisCacheManager(
          RedisConnectionFactory connectionFactory
  ) {

    // Redis 전용 ObjectMapper (전역과 완전히 분리)
    ObjectMapper redisObjectMapper = new ObjectMapper();

    redisObjectMapper.registerModule(new JavaTimeModule());
    redisObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    GenericJackson2JsonRedisSerializer serializer =
            new GenericJackson2JsonRedisSerializer(redisObjectMapper);

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
}

