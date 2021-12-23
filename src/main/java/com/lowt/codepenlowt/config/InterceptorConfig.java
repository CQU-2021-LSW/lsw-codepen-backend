package com.lowt.codepenlowt.config;

import com.lowt.codepenlowt.Interceptors.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    // 添加拦截器

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JWTInterceptor())
//                .addPathPatterns("/**") //拦截所有路径
//                .excludePathPatterns("/user/login")
//                .excludePathPatterns("/user/register")
//                .excludePathPatterns("/user/findBackPwd");
//                //用户登录接口放行不然没办法获取 token
//    }

    // 测试-不拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                .excludePathPatterns("/**");
    }

}
