package com.lojaexpress.orderservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
public class ProductClient {
    private final RestClient restClient;

    public ProductClient(@Value("${product-service.url}") String productServiceUrl) {
        this.restClient = RestClient.builder()
                .baseUrl(productServiceUrl)
                .build();
    }

    public boolean existsById(Long productId) {
        try {
            restClient.get()
                    .uri("/products/{id}", productId)
                    .retrieve()
                    .toBodilessEntity();

            return true;
        } catch (RestClientResponseException exception) {
            return false;
        }
    }
}
