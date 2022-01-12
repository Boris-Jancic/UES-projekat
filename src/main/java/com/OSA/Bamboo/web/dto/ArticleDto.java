package com.OSA.Bamboo.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Name is required")
    private String imageBytes;

    @NotBlank(message = "Description is required")
    private String description;
    @NotNull
    private Double price;
    @NotNull
    private boolean onDiscount;
}
