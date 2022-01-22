package com.OSA.Bamboo.service.impl;

import com.OSA.Bamboo.elastic.ArticleElasticRepo;
import com.OSA.Bamboo.model.Article;
import com.OSA.Bamboo.model.Discount;
import com.OSA.Bamboo.model.elastic.ArticleElastic;
import com.OSA.Bamboo.repository.ArticleRepo;
import com.OSA.Bamboo.repository.DiscountRepo;
import com.OSA.Bamboo.service.ArticleService;
import com.OSA.Bamboo.web.converter.ArticleToDto;
import com.OSA.Bamboo.web.dto.ArticleDto;
import com.OSA.Bamboo.web.dtoElastic.ArticleElasticDto;
import com.OSA.Bamboo.web.elasticConverter.ArticleElasticConverter;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JpaArticleService implements ArticleService {

    private final String imageDirectory = System.getProperty("user.dir") + "/images/";

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    private ArticleElasticRepo articleElasticRepo;

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private ArticleToDto toDto;

    @Autowired
    private ArticleElasticConverter articleElasticConverter;

    @Override
    public List<Article> getAll() {
        return articleRepo.findAll();
    }

    @Override
    public List<ArticleDto> getSellerArticles(Long id) throws IOException {
        List<Discount> discounts = discountRepo.getActualDiscounts(id);
        List<ArticleDto> articlesDto = toDto.convert(articleRepo.getSellerArticles(id));

        for (ArticleDto article : articlesDto) {
            double articlePrice = article.getPrice();
            double discountPrice = 0;
            for (Discount discount : discounts) {
                if (Objects.equals(discount.getArticle().getId(), article.getId())) {
                    discountPrice = articlePrice - articlePrice * (discount.getDiscountPercent() * 0.01);
                    article.setPrice(discountPrice);
                    article.setOnDiscount(true);
                }
            }
        }

        return articlesDto;
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    @Override
    public Article saveNewArticle(String base64Image, String imgName, String name, String description, String price, Long sellerId) {
        //This will decode the String which is encoded by using Base64 class
        byte[] imageByte = Base64.decodeBase64(base64Image);
        makeDirectoryIfNotExist(imageDirectory);
        Path fileNamePath = Paths.get(imageDirectory, imgName);
        Article article = new Article(name, description, Double.parseDouble(price), imgName, sellerId);
        try {
            Files.write(fileNamePath, imageByte);
            this.articleRepo.save(article);
            ArticleElastic articleElastic = new ArticleElastic(
                    articleRepo.getFirstByOrderByIdDesc().getId(),
                    article.getName(),
                    article.getDescription(),
                    article.getPrice());
            articleElasticRepo.save(articleElastic);
            return article;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void update(Article article) throws ParseException {
        Optional<Article> originalArticle = articleRepo.findById(article.getId());
        if (originalArticle.isPresent()) {
            Article newArticle = originalArticle.get();
            newArticle.setName(article.getName());
            newArticle.setDescription(article.getDescription());
            newArticle.setPrice(article.getPrice());
            articleRepo.save(newArticle);
            articleElasticRepo.save(articleElasticConverter.fromOriginalToElastic(newArticle));
        }
    }

    @Override
    public Article save(Article article) throws ParseException {
        articleElasticRepo.save(articleElasticConverter.fromOriginalToElastic(article));
        return articleRepo.save(article);
    }

    @Override
    public Article delete(Long id) {
        Optional<Article> articleOptional = this.articleRepo.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            articleRepo.deleteById(id);
            discountRepo.deleteByArticleId(id);
            articleElasticRepo.deleteById(id);
            return article;
        } else {
            return null;
        }
    }

    @Override
    public Optional<Article> one(Long id) {
        return this.articleRepo.findById(id);
    }

    @Override
    public List<ArticleElasticDto> getArticleDocs(String name) {
        System.out.println(
                articleElasticRepo
                        .findByName(name, PageRequest.of(0, 50))
                        .getContent());
        return articleElasticConverter
                .listToDto(
                    articleElasticRepo
                    .findByName(name, PageRequest.of(0, 50))
                    .getContent());
    }
}
