package com.hanzi.controller;

import com.hanzi.bean.Order;
import com.hanzi.order.properties.OrderProperties;
import com.hanzi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
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

    @Autowired
    private Environment env;

    @GetMapping("/test-config")
    public String testConfig() {
        // 如果这里打印出 5000，说明配置拉取成功了，问题在类绑定上
        // 如果打印 null，说明 Nacos 根本没拉下来，或者文件里没写
        return env.getProperty("order.timeout");
    }

    @GetMapping("/config")
    public String getConfig() {
        return "OrderTimeout+OrderAutoConfirm+dbUrl = " +
                orderProperties.getTimeout() + " : " +
                orderProperties.getAutoConfirm() + " : " +
                orderProperties.getDbUrl();
    }
    @GetMapping(value = "/create")
    public Order createOrder(@RequestParam("userId") Long userId, @RequestParam("productId") Long productId) {
        Order order = orderService.createOrder(userId, productId);
        return order;
    }
}
