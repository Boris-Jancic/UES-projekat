package com.OSA.Bamboo.model;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;



@Data
@NoArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id", unique = true, nullable = false)
    private Long id;

    private String name;

    @Field(type = FieldType.Text)
    private String description;

    private Double price;

    private String imageName;

    private Long sellerId;

    public Article(String name, String description, double price, String imageName, Long sellerId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageName = imageName;
        this.sellerId = sellerId;
    }
}
