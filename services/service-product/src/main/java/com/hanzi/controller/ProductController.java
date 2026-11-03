package com.hanzi.controller;

import com.hanzi.bean.Product;
import com.hanzi.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProductController {
    @Autowired
    ProductService productService;

//    @GetMapping(value = "/productId/{id}")
//    public Product getProductById(@PathVariable("id") Long productId) {
//        log.info("正在远程调用service-product...");
//        Product product = productService.getProductById(productId);
//        return product;
//    }

    // 改造成远程调用
    @GetMapping(value = "/productId/{id}")
    public Product getProductById(@PathVariable("id") Long productId, HttpServletRequest httpServletRequest) {
        String XToken = httpServletRequest.getHeader("X-Token");
        System.out.println("XToken = " + XToken);
        System.out.println("正在远程调用service-product...");
//        int i = 10 / 0;
        Product product = productService.getProductById(productId);
        return product;
    }

}
