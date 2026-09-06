package com.lojaexpress.orderservice.service;

import org.springframework.stereotype.Service;
import com.lojaexpress.orderservice.model.Order;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
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
        order.setId(nextId);
        nextId++;

        orders.add(order);

        return order;
    }
}
