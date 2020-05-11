package com.personal.ride.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * WebMvcConfig
 * @author zhangyk
 * @date 2019/8/8 17:08
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

//    @Autowired
//    private JsonTokenInterceptor jsonTokenInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:"+ File.separator+"META-INF"+ File.separator+"resources"+ File.separator);

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:"+ File.separator+"META-INF"+ File.separator+"resources"+ File.separator+"webjars"+ File.separator);

        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:"+ File.separator+"templates"+ File.separator);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        /**
//         *此处拦截路径（/**）
//         * 注意两个**。一个*号只拦截一级路径下，两个*号拦截所有
//         */
//        registry.addInterceptor(jsonTokenInterceptor).addPathPatterns("/**")
//                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**","/doc.html","/*");
//    }


   /* @Bean
    FilterRegistrationBean tokenFilter() {
        FilterRegistrationBean filterReg = new FilterRegistrationBean(new JsonDataFilter());
        //优先级
        filterReg.setOrder(70);
        filterReg.setDispatcherTypes(DispatcherType.REQUEST);
        //匹配路径
        filterReg.addUrlPatterns("/*");
        filterReg.addInitParameter("exclusions","/api/swagger-resources/**, /api/webjars/**, /api/v2/**, /api/swagger-ui.html/**,/api/doc.html,/*");
        return filterReg;
    }*/
}