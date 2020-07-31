package com.hxh.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.hxh.utils.MapWrapperFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @auth hxh
 * @date 2020/7/16 10:55
 * @Description
 */
//主配置类
@Configuration
@MapperScan("com.hxh.mapper")
@Import({SpringServiceConfig.class,SpringRedisConfig.class,SpringCacheConfig.class})
public class SpringMybatisConfig {
    @Bean
    public DruidDataSource getDruidDataSource() {
        DruidDataSource source = new DruidDataSource();
        Properties properties = new Properties();
        InputStream is = SpringMybatisConfig.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
            source.configFromPropety(properties);
            return source;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(DruidDataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //设置数据源
        factoryBean.setDataSource(dataSource);
//        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCallSettersOnNulls(true);//设置对map的value为null的数据key保持显示
        configuration.setObjectWrapperFactory(new MapWrapperFactory());//自定义map对象的包装工厂
        factoryBean.setConfiguration(configuration);

        //配置分页插件
        PageInterceptor interceptor = new PageInterceptor();
        interceptor.setProperties(new Properties());
        factoryBean.setPlugins(interceptor);

        return factoryBean;
    }
}
