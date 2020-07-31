package com.hxh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @auth hxh
 * @date 2020/7/28 11:01
 * @Description
 */
/*
 * springdata-redis整合流程：
 * 1.引入springdata-redis和jedis依赖
 * 2.添加redis.properties配置文件，并读取到spring环境对象
 * 3.创建redis的连接工厂对象
 * 4.创建RedisTemplate对象
 * 5.需要使用redis缓存的功能类上注入RedisTemplate对象,操作对象api实现redis交互
 * */
@Configuration
@PropertySource(value = "classpath:redis.properties", encoding = "utf-8")
public class SpringRedisConfig {

    @Bean
    public RedisConnectionFactory getConnectionFactory(@Value("${redis.host}") String host,
                                                       @Value("${redis.port}") int port,
                                                       @Value("${redis.password}") String password,
                                                       @Value("${redis.minIdle}") int minIdle
    ) {
        //创建一个Jedis工厂
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        //设置redis的连接参数
        connectionFactory.setHostName(host);
        connectionFactory.setPort(port);
        connectionFactory.setPassword(password);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMinIdle(minIdle);
        connectionFactory.setPoolConfig(poolConfig);
        return connectionFactory;
    }

    @Bean
    public RedisTemplate<Object, Object> getTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        //设置key的序列化器
        StringRedisSerializer stringRedisTemplate=new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisTemplate);
        //设置value的序列化器
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setValueSerializer(serializer);
        //设置hash的序列化器
        redisTemplate.setDefaultSerializer(serializer);
        return redisTemplate;
    }

}
