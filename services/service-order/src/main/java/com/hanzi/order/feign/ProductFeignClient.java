package com.hanzi.order.feign;

import com.hanzi.bean.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product")
public interface ProductFeignClient {
    @GetMapping(value = "/productId/{id}")
    public Product getProductById(@PathVariable("id") Long productId);
}