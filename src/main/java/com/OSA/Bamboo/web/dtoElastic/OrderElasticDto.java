package com.OSA.Bamboo.web.dtoElastic;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderElasticDto {
    private LocalDate hourlyRate;

    private int grade;

    private String comment;

    private String user;
}
