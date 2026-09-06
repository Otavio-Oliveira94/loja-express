package com.lojaexpress.orderservice.controller;

import com.lojaexpress.orderservice.service.OrderService;
import com.lojaexpress.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {
        Order orderCreated = orderService.create(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderCreated);
    }
}
