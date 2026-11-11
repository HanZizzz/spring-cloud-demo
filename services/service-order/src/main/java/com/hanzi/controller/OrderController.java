package com.hanzi.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.hanzi.bean.Order;
import com.hanzi.order.properties.OrderProperties;
import com.hanzi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RefreshScope
@Slf4j
@RestController
@RequestMapping("/api/order")
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

    @GetMapping("/writeDb")
    public String writeDb() {
        return "writeDb success...";
    }
    @GetMapping("/readDb")
    public String readDb() {
        log.info("readDb success...");
        return "readDb success...";
    }

//    @GetMapping("/seckill")
//    public Order seckill(@RequestParam("userId") Long userId,
//                         @RequestParam("productId") Long productId) {
//        Order order = orderService.createOrder(userId, productId);
//        order.setId(Long.MAX_VALUE);
//        return order;
//    }

    @GetMapping("/seckill")
    @SentinelResource(value = "seckill-order",fallback = "seckillFallback") //此处用于测试热点规则
//    public Order seckill(@RequestParam("userId") Long userId,
//                         @RequestParam("productId") Long productId) {
//此处模拟不传参数，给默认参数
//    public Order seckill(@RequestParam(value = "userId",defaultValue = "888") Long userId,
//                         @RequestParam(value = "productId",defaultValue = "1000") Long productId) {
    public Order seckill(@RequestParam(value = "userId",required = false) Long userId,
                         @RequestParam(value = "productId",required = false) Long productId) {
        Order order = orderService.createOrder(userId, productId);
        order.setId(Long.MAX_VALUE);
        return order;
    }

    public Order seckillFallback(Long userId, Long productId, Throwable exception) {
        System.out.println("seckillFallback...");
        Order order = new Order();
        order.setId(productId);
        order.setUserId(userId);
        order.setAddress("seckillFallback异常信息" + exception.getClass());
        return order;
    }
}
