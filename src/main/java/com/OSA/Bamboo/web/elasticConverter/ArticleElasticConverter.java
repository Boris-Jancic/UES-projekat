package com.OSA.Bamboo.web.elasticConverter;

import com.OSA.Bamboo.model.Article;
import com.OSA.Bamboo.model.elastic.ArticleElastic;
import com.OSA.Bamboo.web.dto.ArticleDto;
import com.OSA.Bamboo.web.dtoElastic.ArticleEditDto;
import com.OSA.Bamboo.web.dtoElastic.ArticleElasticDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleElasticConverter {

    @Autowired
    private ModelMapper modelMapper;

    public ArticleElasticDto toDto(ArticleElastic entity) {
        return modelMapper.map(entity, ArticleElasticDto.class);
    }

    public ArticleElastic toEntity(ArticleElasticDto dto) throws ParseException {
        return modelMapper.map(dto, ArticleElastic.class);
    }

    public ArticleElastic fromOriginalToElastic(Article article) throws ParseException {
        return modelMapper.map(article, ArticleElastic.class);
    }

    public Article fromEditToEntity(ArticleEditDto dto) {
        return modelMapper.map(dto, Article.class);
    }

    public List<ArticleElasticDto> listToDto(List<ArticleElastic> entities){
        List<ArticleElasticDto> dtos = new ArrayList<>();
        for (ArticleElastic a : entities) {
            dtos.add(toDto(a));
        }
        return dtos;
    }

    public List<ArticleElastic> listToEntity(List<ArticleElasticDto> dtos) throws ParseException {
        List<ArticleElastic> articles = new ArrayList<>();
        for (ArticleElasticDto s : dtos) {
            articles.add(toEntity(s));
        }
        return articles;
    }

    public static ArticleElastic mapModel(ArticleElasticDto dto) {
        return ArticleElastic.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }

    public static ArticleElasticDto mapResponseDto(ArticleElastic articleElastic) {
        return ArticleElasticDto.builder()
                .id(articleElastic.getId())
                .name(articleElastic.getName())
                .description(articleElastic.getDescription())
                .price(articleElastic.getPrice())
                .build();
    }

    public static List<ArticleElasticDto> mapDtosFromSearchHit(SearchHits<ArticleElastic> searchHits) {
        return searchHits
                .map(article -> mapResponseDto(article.getContent()))
                .toList();
    }
}
