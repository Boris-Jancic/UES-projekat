package com.OSA.Bamboo.web.dtoElastic;

import lombok.Data;

@Data
public class ArticleElasticDto {
    private Long id;

    private String name;

    private String description;

    private Double price;
}
