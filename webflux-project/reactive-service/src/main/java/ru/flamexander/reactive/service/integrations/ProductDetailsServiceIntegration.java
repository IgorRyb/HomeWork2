package ru.flamexander.reactive.service.integrations;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.flamexander.reactive.service.dtos.ProductDetailsDto;
import ru.flamexander.reactive.service.exceptions.AppException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductDetailsServiceIntegration {
    private static final Logger logger = LoggerFactory.getLogger(ProductDetailsServiceIntegration.class.getName());

    private final WebClient productDetailsServiceWebClient;

    public Mono<ProductDetailsDto> getProductDetailsById(Long id) {
        logger.info("SEND REQUEST FOR PRODUCT_DETAILS-ID: {}", id);
        return productDetailsServiceWebClient.get()
                .uri("/api/v1/details/{id}", id)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.equals(HttpStatus.NOT_FOUND),
                        clientResponse -> Mono.error(new AppException("PRODUCT_NOT_FOUND"))
                )
                .onStatus(
                        httpStatus -> httpStatus.isError(),
                        clientResponse -> Mono.error(new AppException("PRODUCT_DETAILS_SERVICE_INTEGRATION_ERROR"))
                )
                .bodyToMono(ProductDetailsDto.class)
                .log();
    }

    public Flux<ProductDetailsDto> getAllProductDetails() {
        logger.info("SEND REQUEST FOR ALL PRODUCT_DETAILS: {}");
        return productDetailsServiceWebClient.get()
                .uri("/api/v1/details/all")
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.isError(),
                        clientResponse -> Mono.error(new AppException("PRODUCT_DETAILS_SERVICE_INTEGRATION_ERROR"))
                )
                .bodyToFlux(ProductDetailsDto.class)
                .log();
    }

    public Flux<ProductDetailsDto> getProductsDetailsByIds(List<Long> ids) {
        logger.info("SEND REQUEST FOR PRODUCT_DETAILS-IDS: {}", ids);
        String joinedId = String.join(",", ids.toString());
        return productDetailsServiceWebClient.get()
                .uri("/api/v1/details/{ids}", joinedId)
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.isError(),
                        clientResponse -> Mono.error(new AppException("PRODUCT_DETAILS_SERVICE_INTEGRATION_ERROR"))
                )
                .bodyToFlux(ProductDetailsDto.class)
                .log();
    }
}
