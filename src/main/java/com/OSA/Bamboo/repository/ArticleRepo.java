package com.OSA.Bamboo.repository;

import com.OSA.Bamboo.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    @Query(value = "SELECT a FROM Article a WHERE a.sellerId = ?1")
    List<Article> getSellerArticles(Long sellerId);

    Article getFirstByOrderByIdDesc();

    List<Article> getArticleByPriceBetween(Double min, Double max);

    @Query(value = "SELECT art FROM Article art WHERE art.id " +
            " IN (SELECT o.article.id FROM OrderedArticle o WHERE o.orderId " +
            " IN (SELECT bo.id FROM BuyerOrder bo WHERE bo.delivered = true " +
            " GROUP BY bo.id" +
            " HAVING avg(bo.grade) BETWEEN ?1 AND ?2))")
    List<Article> getArticleByAverageGradeBetween(Double min, Double max);

    @Query(value = "SELECT art FROM Article art WHERE art.id " +
            " IN (SELECT o.article.id FROM OrderedArticle o WHERE o.orderId " +
            " IN (SELECT bo.id FROM BuyerOrder bo WHERE bo.delivered = true " +
            " GROUP BY bo.id" +
            " HAVING COUNT(*) BETWEEN ?1 AND ?2))")
    List<Article> getArticleByCommentNumberBetween(int min, int max);
}
