package com.OSA.Bamboo.elastic;

import com.OSA.Bamboo.model.elastic.OrderElastic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderElasticRepo extends ElasticsearchRepository<OrderElastic, String> {
    Page<OrderElastic> findByComment(String comment, Pageable pageable);
}
