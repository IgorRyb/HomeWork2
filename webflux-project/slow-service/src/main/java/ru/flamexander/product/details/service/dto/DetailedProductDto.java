package ru.flamexander.product.details.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedProductDto {

    private Long id;
    private String name;
    private String description;
}
