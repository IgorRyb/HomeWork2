package ru.flamexander.reactive.service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.CorePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.DetailedProductDto;
import ru.flamexander.reactive.service.dtos.ProductDetailsDto;
import ru.flamexander.reactive.service.entities.Product;
import ru.flamexander.reactive.service.exceptions.AppException;
import ru.flamexander.reactive.service.services.ProductDetailsService;
import ru.flamexander.reactive.service.services.ProductsService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/detailed")
@RequiredArgsConstructor
public class ProductsDetailsController {

    private final ProductDetailsService productDetailsService;

    private final ProductsService productsService;

    @GetMapping("/demo")
    public Flux<ProductDetailsDto> getManySlowProducts() {
        Mono<ProductDetailsDto> p1 = productDetailsService.getProductDetailsById(1L);
        Mono<ProductDetailsDto> p2 = productDetailsService.getProductDetailsById(2L);
        Mono<ProductDetailsDto> p3 = productDetailsService.getProductDetailsById(3L);
        return p1.mergeWith(p2).mergeWith(p3);
    }

    @GetMapping("/{ids}")
    public CorePublisher<DetailedProductDto> getProductsWithDetailsByIds(@PathVariable("ids") List<Long> ids) {
        if (ids.size() == 1) {
            return getDetailedProductById(ids.get(0));
        }
        return combinedProductProductAndDetails(
                productsService.findByIds(ids),
                productDetailsService.getProductsDetailsByIds(ids));
    }

    public Mono<DetailedProductDto> getDetailedProductById(@PathVariable("id") Long id) {
        Mono<Product> productMono = productsService.findById(id);
        Mono<ProductDetailsDto> detailsDto = productDetailsService.getProductDetailsById(id);
        return getMonoCombinedProductAndDetails(productMono, detailsDto);
    }

    @GetMapping("/all")
    public Flux<DetailedProductDto> getAllProductsWithDetails() {
        return combinedProductProductAndDetails(
                productsService.findAll(),
                productDetailsService.getAllProductDetails()
        );
    }

    private Mono<DetailedProductDto> getMonoCombinedProductAndDetails(Mono<Product> product,
                                                                  Mono<ProductDetailsDto> details) {
        return Mono.zip(product, details)
                .map(tuple -> {
                    Product product1 = tuple.getT1();
                    ProductDetailsDto details1 = tuple.getT2();
                    return new DetailedProductDto(product1.getId(), details1.getDescription(), product1.getName());
                });
    }

    private Flux<DetailedProductDto> combinedProductProductAndDetails(Flux<Product> productDtos,
                                                                             Flux<ProductDetailsDto> detailsDtos) {
        return productDtos.flatMap(product -> detailsDtos
                .filter(details -> details.getId().equals(product.getId()))
                .next()
                .map(details -> new DetailedProductDto(
                        product.getId(),
                        details.getDescription(),
                        product.getName()
                ))
                .onErrorReturn(new DetailedProductDto(product.getId(), product.getName()))
        );
    }

}
