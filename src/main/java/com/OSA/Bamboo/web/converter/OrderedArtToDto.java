package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.OrderedArticle;
import com.OSA.Bamboo.service.ArticleService;
import com.OSA.Bamboo.web.dto.OrderedArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class OrderedArtToDto implements Converter<OrderedArticle, OrderedArticleDto> {

    @Autowired
    private ArticleService articleService;

    @Override
    public OrderedArticleDto convert(OrderedArticle orderedArticle) {
        OrderedArticleDto dto = new OrderedArticleDto();
        dto.setQuanity(orderedArticle.getQuanity());
        dto.setArticleId(orderedArticle.getId());
        return dto;
    }
}