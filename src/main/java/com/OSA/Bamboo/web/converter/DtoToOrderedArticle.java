package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.Article;
import com.OSA.Bamboo.model.OrderedArticle;
import com.OSA.Bamboo.service.ArticleService;
import com.OSA.Bamboo.web.dto.OrderedArticleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DtoToOrderedArticle implements Converter<OrderedArticleDto, OrderedArticle> {

    @Autowired
    private ArticleService articleService;

    @Override
    public OrderedArticle convert(OrderedArticleDto dto) {
        OrderedArticle orderedArticle = new OrderedArticle();
        Optional<Article> article = articleService.one(dto.getArticleId());
        if (article.isPresent())
            orderedArticle.setArticle(article.get());
        else
            orderedArticle.setArticle(null);
        orderedArticle.setQuanity(dto.getQuanity());
        return orderedArticle;
    }
}