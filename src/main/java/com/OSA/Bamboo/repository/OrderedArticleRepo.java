package com.OSA.Bamboo.repository;

import com.OSA.Bamboo.model.OrderedArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedArticleRepo extends JpaRepository<OrderedArticle, Long> {

}