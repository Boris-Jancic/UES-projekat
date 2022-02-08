package com.OSA.Bamboo.web.dtoElastic;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class OrderElasticDto {
    private LocalDate hourlyRate;

    private int grade;

    private String comment;

    private String username;
}
