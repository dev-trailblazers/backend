package com.growth.community.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.growth.community.domain.auth.SMSAuthenticationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.nio.charset.StandardCharsets;


@RequiredArgsConstructor
@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;

    private final ObjectMapper objectMapper;


    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }

    @Bean
    RedisTemplate<String, SMSAuthenticationInfo> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, SMSAuthenticationInfo> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new StringRedisSerializer()); // 키를 문자열로 직렬화
        template.setValueSerializer(new SMSAuthenticationInfoSerializer()); // 값은 별도의 직렬화 로직을 사용

        return template;
    }

    public class SMSAuthenticationInfoSerializer implements RedisSerializer<SMSAuthenticationInfo> {

        @Override
        public byte[] serialize(SMSAuthenticationInfo smsAuthenticationInfo) throws SerializationException {
            try {
                return objectMapper.writeValueAsString(smsAuthenticationInfo).getBytes(StandardCharsets.UTF_8);
            } catch (JsonProcessingException e) {
                throw new SerializationException("Error serializing object", e);
            }
        }

        @Override
        public SMSAuthenticationInfo deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null) {
                return null;
            }
            try {
                String json = new String(bytes, StandardCharsets.UTF_8);
                return objectMapper.readValue(json, SMSAuthenticationInfo.class);
            } catch (JsonProcessingException e) {
                throw new SerializationException("Error deserializing object", e);
            }
        }
    }
}
