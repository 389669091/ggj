package com.hxh.config;

import com.hxh.inteceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 1.开启springmvc注解支持
 * 2.开启静态资源放行
 * 3.设置自定义视图解析器
 * 4.开启controller包扫描
 */
@Configuration
@EnableWebMvc  //mvc注解支持
@ComponentScan("com.hxh.controller")
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();//静态资源放行
    }
    //自定义视图解析器
    @Bean
    public InternalResourceViewResolver getViewResolver(){
        return new InternalResourceViewResolver("/WEB-INF/html",".html");
    }

    /*
     * CommonsMultipartResolver:以commons的fileupload组件实现的文件上传解析器对象
     * 解析Request中的文件流封装成MultipartFile对象
     * */
    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        return multipartResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor=new LoginInterceptor();
        InterceptorRegistration registration=new InterceptorRegistration(loginInterceptor);
        registration.addPathPatterns("/**");
        registration.excludePathPatterns("/main/login","/main/logout");
    }
}
