package com.OSA.Bamboo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class OrderedArticle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(length = 10)
    private Long id;

    private int quanity;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "article_id")
    private Article article;

    private Long orderId;
}
