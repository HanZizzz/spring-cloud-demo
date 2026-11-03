package com.hanzi;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(value = "com.hanzi.order.feign")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }

    //1、项目启动就监听配置文件变化
    //2、发生变化后拿到变化值
    //3、发送邮件
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
//        return new ApplicationRunner() {
//            @Override
//            public void run(ApplicationArguments args) throws Exception {
//
//            }
//        };

        return args -> {
            //这个监听的服务和application.yml中naocs的配置中心有关
            ConfigService configService = nacosConfigManager.getConfigService();
            configService.addListener("service-order.properties", "DEFAULT_GROUP", new Listener() {
                @Override
                public Executor getExecutor() {
                    return Executors.newFixedThreadPool(4);
                }
                @Override
                public void receiveConfigInfo(String s) {
                    log.info("变化的配置信息：" + s);
                    log.info("邮件通知....");
                }
            });
            log.info("=========");
        };
    }

}