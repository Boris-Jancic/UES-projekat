package com.OSA.Bamboo.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;

@Data
@NoArgsConstructor
public class BuyerOrderDto {

    private Long id;

    private String hourlyRate;

    private boolean delivered;

    @Max(value = 5)
    private int grade;

    private String comment;

    private boolean anonymousComment;

    private boolean archivedComment;

    private String username;
}
