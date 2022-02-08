package com.OSA.Bamboo.web.dtoElastic;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ArticleElasticDto {
    private Long id;

    private String name;

    private String description;

    private Double price;
}
