package com.hanzi.controller;

import com.hanzi.bean.Order;
import com.hanzi.order.properties.OrderProperties;
import com.hanzi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope
@RestController
public class OrderController {
    @Autowired
    OrderService orderService;
    //    @Value("${order.timeout}")
    //    String orderTimeout;
    //    @Value("${order.auto-confirm}")
    //    String orderAutoConfirm;

    @Autowired
    OrderProperties orderProperties;

    @GetMapping("/config")
    public String getConfig() {
        return "OrderTimeout+OrderAutoConfirm = " + orderProperties.getTimeout() + " : " + orderProperties.getAutoConfirm();
    }
    @GetMapping(value = "/create")
    public Order createOrder(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        Order order = orderService.createOrder(userId, productId);
        return order;
    }
}
