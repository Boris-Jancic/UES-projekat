package com.OSA.Bamboo.service;

import com.OSA.Bamboo.model.Article;
import com.OSA.Bamboo.web.dto.ArticleDto;
import com.OSA.Bamboo.web.dtoElastic.ArticleElasticDto;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<Article> getAll();

    List<ArticleDto> getSellerArticles(Long id) throws IOException;

    Article saveNewArticle(String base64Image, String imgName, String name, String description, String price, Long sellerId);

    Article save(Article article) throws ParseException;

    Article delete(Long id);

    Optional<Article> one(Long id);

    List<ArticleElasticDto> getArticleDocs(String name);

    void update(Article article) throws ParseException;

    List<ArticleDto> getArticlesByPrice(Double min, Double max) throws IOException;

    List<ArticleDto> getArticlesByComment(int min, int max) throws IOException;

    List<ArticleDto> getArticlesByGrade(Double min, Double max) throws IOException;
}
