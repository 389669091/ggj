package com.hxh.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth hxh
 * @date 2020/7/28 17:16
 * @Description
 */
/*
 * spring的缓存框架配置类，开发流程：
 * 1.配置spring的缓存管理器对象，设置缓存管理器对象中管理的缓存名字
 * 2.开启缓存注解支持
 * 3.在需要使用缓存的类或者方法上添加缓存注解，设置key和对应的缓存名字
 * */
@Configuration
@EnableCaching//开启缓存
public class SpringCacheConfig {

    @Bean
    public CacheManager getCacheManager(RedisOperations redisOperations){
        //创建一个缓存管理对象
        RedisCacheManager cacheManager=new RedisCacheManager(redisOperations);
        //定义一个list集合
        List<String> cacheNames=new ArrayList<>();
        cacheNames.add("officeCache");
        //将集合中的缓存对象名加入缓存管理对象
        cacheManager.setCacheNames(cacheNames);
        //设置缓存对象存活的时间,单位秒
        cacheManager.setDefaultExpiration(6000);
        return cacheManager;
    }
}
