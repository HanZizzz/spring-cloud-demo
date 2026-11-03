package com.hanzi.order.feign;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class WeatherTest {
    @Autowired
    WeatherFeignClient weatherFeignClient;
    @Test
    public void test() {
//        String weather = weatherFeignClient.getWeather("自己的AppCode",
//                "50b53ff8dd7d9fa320d3d3ca32cf8ed1","2182");
//        System.out.println("weather = " + weather);

        HashMap<String, Integer> params = new HashMap<>();
        params.put("cityId", 2);
        params.put("cityType", 0);
        String weather = weatherFeignClient.getWeather(params);
        System.out.println("weather = " + weather);
    }
}
