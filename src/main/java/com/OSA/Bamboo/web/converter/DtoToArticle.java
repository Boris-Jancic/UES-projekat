package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.Article;
import com.OSA.Bamboo.web.dto.ArticleDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@Component
public class DtoToArticle implements Converter<ArticleDto, Article> {
    @Override
    public Article convert(ArticleDto articleDto) {
        Article article = new Article();
        article.setName(articleDto.getName());
        article.setDescription(articleDto.getDescription());
        article.setPrice(articleDto.getPrice());
        return article;
    }
}
