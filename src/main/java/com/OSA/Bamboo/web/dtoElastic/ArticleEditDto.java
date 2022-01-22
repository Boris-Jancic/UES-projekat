package com.OSA.Bamboo.web.dtoElastic;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ArticleEditDto {

    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull
    private Double price;

    @NotBlank(message = "Name is required")
    private String imageName;
}
