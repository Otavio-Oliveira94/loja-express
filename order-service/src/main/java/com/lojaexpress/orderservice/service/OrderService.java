package com.lojaexpress.orderservice.service;

import com.lojaexpress.orderservice.client.ProductClient;
import com.lojaexpress.orderservice.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lojaexpress.orderservice.model.Order;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductClient productClient;
    private final List<Order> orders = new ArrayList<>();
    private Long nextId = 1L;

    public List<Order> findAll() {
        return orders;
    }

    public Order findById(Long id) {
        for (Order order : orders) {
            if (order.getId().equals(id)) {
                return order;
            }
        }

        return null;
    }

    public Order create(Order order) {
        if (!productClient.existsById(order.getProductId())) {
            throw new ProductNotFoundException();
        }

        order.setId(nextId);
        nextId++;

        orders.add(order);

        return order;
    }
}
