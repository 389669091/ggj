package com.hxh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.hxh.service")//开启包扫描 扫描服务层
@EnableTransactionManagement   //开启事务注解支持
@PropertySource(value="classpath:system.properties",encoding = "utf-8")
public class SpringServiceConfig {


    @Bean   //创建事务管理器对象
    public DataSourceTransactionManager getTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }


}
