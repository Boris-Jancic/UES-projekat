package com.OSA.Bamboo.web.rest.impl;

import com.OSA.Bamboo.elastic.ArticleElasticRepo;
import com.OSA.Bamboo.model.Article;
import com.OSA.Bamboo.model.elastic.ArticleElastic;
import com.OSA.Bamboo.service.impl.JpaArticleService;
import com.OSA.Bamboo.web.converter.ArticleToDto;
import com.OSA.Bamboo.web.converter.DtoToArticle;
import com.OSA.Bamboo.web.dto.ArticleDto;
import com.OSA.Bamboo.web.rest.ArticleApi;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class ArticleApiImpl implements ArticleApi {

    @Autowired
    private JpaArticleService articleService;

    @Autowired
    private ArticleToDto toDto;
    @Autowired
    private DtoToArticle toEntity;

    @SneakyThrows
    @Override
    public ResponseEntity<?> addArticle(String base64Image, String imgName, String name, String description, String price, Long sellerId) {
        Article article = articleService.saveNewArticle(base64Image, imgName, name, description, price, sellerId);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity getAllArticles() throws IOException {
        List<ArticleDto> articles = toDto.convert(articleService.getAll());
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity testElasticIndex(ArticleDto dto) throws IOException {
        Article article = toEntity.convert(dto);
        assert article != null;
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getElasticArticles(String name) {
        return new ResponseEntity<>(articleService.getArticleDocs(name), HttpStatus.OK);
    }

    @Override
    public ResponseEntity getSellerArticles(Long id) throws IOException {
        List<ArticleDto> articles = articleService.getSellerArticles(id);
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @Override
    public ResponseEntity getArticle(Long id) {
        return new ResponseEntity<>(articleService.one(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateArticle(ArticleDto dto) {
        Article article = toEntity.convert(dto);
        if (article != null) {
            articleService.save(article);
            return new ResponseEntity<>("Updated article" + article, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<?> deleteArticle(Long id) {
        articleService.delete(id);
        return new ResponseEntity<>("Article deleted", HttpStatus.NO_CONTENT);
    }
}
