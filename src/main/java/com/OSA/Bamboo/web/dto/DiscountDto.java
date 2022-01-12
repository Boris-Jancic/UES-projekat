package com.OSA.Bamboo.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
public class DiscountDto {

    private String discountPercent;
    @NotNull(message = "From date is required")
    private Date fromDate;
    @NotNull(message = "Till date is required")
    private Date tillDate;
    @NotBlank(message = "Discount reason is required")
    private String description;
    private Long articleId;
    private Long sellerId;
}
