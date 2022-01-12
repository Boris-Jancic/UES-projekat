package com.OSA.Bamboo.service;

import com.OSA.Bamboo.model.Article;
import com.OSA.Bamboo.model.elastic.ArticleElastic;
import com.OSA.Bamboo.web.dto.ArticleDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ArticleService {

    List<Article> getAll();

    List<ArticleDto> getSellerArticles(Long id) throws IOException;

    Article saveNewArticle(String base64Image, String imgName, String name, String description, String price, Long sellerId);

    Article save(Article article);

    Article delete(Long id);

    Optional<Article> one(Long id);

    List<ArticleElastic> getArticleDocs(String name);
}
