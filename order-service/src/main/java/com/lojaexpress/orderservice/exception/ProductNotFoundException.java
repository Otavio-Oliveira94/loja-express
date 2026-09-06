package com.lojaexpress.orderservice.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Produto não encontrado.");
    }
}
