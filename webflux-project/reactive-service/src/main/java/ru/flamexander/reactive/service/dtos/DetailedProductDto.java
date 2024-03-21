package ru.flamexander.reactive.service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedProductDto {

    private Long id;
    private String description;
    private String name;

    public DetailedProductDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
