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
import org.apache.tomcat.util.codec.binary.Base64;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class JpaArticleService implements ArticleService {

    private final String imageDirectory = System.getProperty("user.dir") + "/images/";

    @Autowired
    private ArticleRepo articleRepo;

    @Autowired
    ArticleElasticRepo articleElasticRepo;

    @Autowired
    ElasticsearchOperations elasticsearchTemplate;

    @Autowired
    private DiscountRepo discountRepo;

    @Autowired
    private ArticleToDto toDto;

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
                if (discount.getArticle().getId() == article.getId()) {
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
                    article.getName(),
                    article.getDescription(),
                    article.getPrice(),
                    articleRepo.getFirstByOrderByIdDesc().getId());
            articleElasticRepo.save(articleElastic);
            return article;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public Article save(Article article) {
        return articleRepo.save(article);
    }

    @Override
    public Article delete(Long id) {
        Optional<Article> articleOptional = this.articleRepo.findById(id);
        if (articleOptional.isPresent()) {
            Article article = articleOptional.get();
            articleRepo.deleteById(id);
            discountRepo.deleteByArticleId(id);
            articleElasticRepo.deleteByJpaId(id);
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
    public List<ArticleElastic> getArticleDocs(String name) {
        return articleElasticRepo.findByName(name, PageRequest.of(0, 50)).getContent();
    }
}
