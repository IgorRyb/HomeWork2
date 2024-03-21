package ru.flamexander.reactive.service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.ProductDetailsDto;
import ru.flamexander.reactive.service.integrations.ProductDetailsServiceIntegration;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductDetailsService {
    private final ProductDetailsServiceIntegration productDetailsServiceIntegration;

    public Mono<ProductDetailsDto> getProductDetailsById(Long id) {
        return productDetailsServiceIntegration.getProductDetailsById(id);
    }

    public Flux<ProductDetailsDto> getAllProductDetails() {
        return productDetailsServiceIntegration.getAllProductDetails();
    }

    public Flux<ProductDetailsDto> getProductsDetailsByIds(List<Long> ids) {
        return productDetailsServiceIntegration.getProductsDetailsByIds(ids);
    }
}