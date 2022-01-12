package com.OSA.Bamboo.elastic;

import com.OSA.Bamboo.model.elastic.ArticleElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ArticleElasticRepo extends ElasticsearchRepository<ArticleElastic, String> {
    void deleteByJpaId(Long id);

    List<ArticleElastic> findAllByName(String name);

    Page<ArticleElastic> findByName(String name, Pageable pageable);
}
