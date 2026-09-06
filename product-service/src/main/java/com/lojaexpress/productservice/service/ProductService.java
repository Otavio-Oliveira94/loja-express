package com.lojaexpress.productservice.service;

import com.lojaexpress.productservice.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private Long nextId = 1L;

    public List<Product> findAll() {
        return products;
    }

    public Product findById(Long id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product create(Product product) {
        product.setId(nextId);
        nextId++;

        products.add(product);

        return product;
    }
}
