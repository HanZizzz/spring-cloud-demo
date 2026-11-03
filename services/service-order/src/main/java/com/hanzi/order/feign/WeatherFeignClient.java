package com.hanzi.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

//@FeignClient(value = "weather-client", url = "http://aliv18.data.moji.com")
@FeignClient(value = "weather-client", url = "https://h5ctywhr.api.moji.com")
public interface WeatherFeignClient {
//    @PostMapping("/whapi/json/alicityweather/condition")
//    String getWeather(@RequestHeader("Authorization") String auth,
//                      @RequestParam("token") String token,
//                      @RequestParam("cityId") String cityId);

//    @PostMapping("/weatherDetail")
//    String getWeather(@RequestParam("cityId") Integer cityId,
//                      @RequestParam("cityType") Integer cityType);

    @PostMapping("/weatherDetail")
    String getWeather(@RequestBody Map<String, Integer> params);
}
