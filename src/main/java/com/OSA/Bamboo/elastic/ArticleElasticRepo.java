package com.OSA.Bamboo.elastic;

import com.OSA.Bamboo.model.elastic.ArticleElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ArticleElasticRepo extends ElasticsearchRepository<ArticleElastic, String> {
    void deleteById(Long id);

    Page<ArticleElastic> findByName(String name, Pageable pageable);
}
