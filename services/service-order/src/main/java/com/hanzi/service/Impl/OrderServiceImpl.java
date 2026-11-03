package com.hanzi.service.Impl;

import com.hanzi.bean.Order;
import com.hanzi.bean.Product;
import com.hanzi.order.feign.ProductFeignClient;
import com.hanzi.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

//@Service
//@Slf4j
//public class OrderServiceImpl implements OrderService {
//    @Autowired
//    DiscoveryClient discoveryClient;
//    @Autowired
//    RestTemplate restTemplate;
//    @Override
//    public Order createOrder(Long userId, Long productId) {
//        Product product = getProductFromRemote(productId);
//
//        Order order = new Order();
//        order.setId(1L);
//        // 总金额=价格*数量
//        BigDecimal price = product.getPrice();//价格
//        int num = product.getNum();//数量
//        order.setTotalAmount(price.multiply(new BigDecimal(num)));//总价
//        order.setUserId(userId);
//        order.setNickName("张三");
//        order.setAddress("火星");
//        // 远程查询商品列表
//        order.setProductList(Arrays.asList(product));
//        return order;
//    }
//
//    //远程调用获取商品信息
//    public Product getProductFromRemote(Long productId) {
//        //1、获取到商品服务所在的所有机器IP+port
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
//        ServiceInstance instance = instances.get(0);
//        //远程URL
//        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/productId/" + productId;
//        log.info("远程请求：{}", url);
//        //2、给远程发送请求
//        Product product = restTemplate.getForObject(url, Product.class);
//        return product;
//    }
//}

//@Service
//@Slf4j
//public class OrderServiceImpl implements OrderService {
//    @Autowired
//    DiscoveryClient discoveryClient;
//    @Autowired
//    RestTemplate restTemplate;
//    @Autowired
//    LoadBalancerClient loadBalancerClient;
//    @Override
//    public Order createOrder(Long userId, Long productId) {
//        Product product = this.getProductFromRemoteWithLoadBalance(productId);
//        Order order = new Order();
//        order.setId(1L);
//        // 总金额=价格*数量
//        BigDecimal price = product.getPrice();//价格
//        int num = product.getNum();//数量
//        order.setTotalAmount(price.multiply(new BigDecimal(num)));//总价
//        order.setUserId(userId);
//        order.setNickName("张三");
//        order.setAddress("火星");
//        // 远程查询商品列表
//        order.setProductList(Arrays.asList(product));
//        return order;
//    }
//
//    //阶段二：加入负载均衡
//    public Product getProductFromRemoteWithLoadBalance(Long productId) {
//        //1、获取到商品服务所在的所有机器IP+port
//        ServiceInstance choose = loadBalancerClient.choose("service-product");
//        //远程URL
//        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/productId/" + productId;
//        log.info("远程请求：{}", url);
//        //2、给远程发送请求
//        Product product = restTemplate.getForObject(url, Product.class);
//        return product;
//    }
//
//    //远程调用获取商品信息
//    public Product getProductFromRemote(Long productId) {
//        //1、获取到商品服务所在的所有机器IP+port
//        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
//        ServiceInstance instance = instances.get(0);
//        //远程URL
//        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/productId/" + productId;
//        log.info("远程请求：{}", url);
//        //2、给远程发送请求
//        Product product = restTemplate.getForObject(url, Product.class);
//        return product;
//    }
//}

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    ProductFeignClient productFeignClient;

//    @Override
//    public Order createOrder(Long userId, Long productId) {
//        //        Product product = this.getProductFromRemote(productId);
//        //        Product product = this.getProductFromRemoteWithLoadBalance(productId);
//        Product product = this.getProductFromRemoteWithLoadBalanceAnnotation(productId);
//        Order order = new Order();
//        order.setId(1L);
//        // 总金额=价格*数量
//        BigDecimal price = product.getPrice();//价格
//        int num = product.getNum();//数量
//        order.setTotalAmount(price.multiply(new BigDecimal(num)));//总价
//        order.setUserId(userId);
//        order.setNickName("张三");
//        order.setAddress("火星");
//        // 远程查询商品列表
//        order.setProductList(Arrays.asList(product));
//        return order;
//    }

    @Override
    public Order createOrder(Long userId, Long productId) {
        //        Product product = this.getProductFromRemote(productId);
        //        Product product = this.getProductFromRemoteWithLoadBalance(productId);
//        Product product = this.getProductFromRemoteWithLoadBalanceAnnotation(productId);
        // 注解式远程调用客户端
        Product product = productFeignClient.getProductById(productId);
        Order order = new Order();
        order.setId(1L);
        // 总金额=价格*数量
        BigDecimal price = product.getPrice();//价格
        int num = product.getNum();//数量
        order.setTotalAmount(price.multiply(new BigDecimal(num)));//总价
        order.setUserId(userId);
        order.setNickName("张三");
        order.setAddress("火星");
        // 远程查询商品列表
        order.setProductList(Arrays.asList(product));
        return order;
    }



    //阶段三：于注解的负载均衡
    public Product getProductFromRemoteWithLoadBalanceAnnotation(Long productId) {
        //给远程发送请求；；service-product会被动态替换
        String url = "http://service-product/productId/" + productId;
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    //阶段二：加入负载均衡
    public Product getProductFromRemoteWithLoadBalance(Long productId) {
        //1、获取到商品服务所在的所有机器IP+port
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        //远程URL
        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/productId/" + productId;
        log.info("远程请求：{}", url);
        //2、给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }

    //远程调用获取商品信息
    public Product getProductFromRemote(Long productId) {
        //1、获取到商品服务所在的所有机器IP+port
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance instance = instances.get(0);
        //远程URL
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/productId/" + productId;
        log.info("远程请求：{}", url);
        //2、给远程发送请求
        Product product = restTemplate.getForObject(url, Product.class);
        return product;
    }
}
