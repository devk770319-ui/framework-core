package com.framwork.core.common.cors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.framwork.core.common.message.CommonMessageService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class CorsFilterConfig {

    @Bean
    public FilterRegistrationBean<AllowedOriginCorsFilter> allowedOriginCorsFilter(
            CorsPolicyProperties corsPolicyProperties,
            ObjectMapper objectMapper,
            CommonMessageService commonMessageService
    ) {
        FilterRegistrationBean<AllowedOriginCorsFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AllowedOriginCorsFilter(corsPolicyProperties, objectMapper, commonMessageService));
        bean.addUrlPatterns("/*");
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
