package com.growth.community.config;

import com.growth.community.domain.user.SMSAuthenticationInfo;
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

import java.io.*;


@Configuration
public class RedisConfig {
    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private int port;


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
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
                 ObjectOutputStream oos = new ObjectOutputStream(bos)) {
                oos.writeObject(smsAuthenticationInfo);
                return bos.toByteArray();
            } catch (IOException e) {
                throw new SerializationException("Error serializing object", e);
            }
        }

        @Override
        public SMSAuthenticationInfo deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null) {
                return null;
            }
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
                return (SMSAuthenticationInfo) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new SerializationException("Error deserializing object", e);
            }
        }
    }
}
