package com.hanzi.config;

import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OrderConfig {

    @Bean
    @LoadBalanced //基于注解式的负载均衡
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 重试机制
    @Bean
    Retryer retryer() {
        return new Retryer.Default();
    }
}
