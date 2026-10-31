package com.hanzi.service;

import com.hanzi.bean.Order;

public interface OrderService {
    Order createOrder(Long userId, Long productId);
}
