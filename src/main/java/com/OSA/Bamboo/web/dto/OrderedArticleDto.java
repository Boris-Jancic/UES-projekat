package com.OSA.Bamboo.web.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class OrderedArticleDto {
    @NotNull
    private int quanity;
    @NotNull
    private long articleId;
}
