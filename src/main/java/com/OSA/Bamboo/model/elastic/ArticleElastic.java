package com.OSA.Bamboo.model.elastic;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Data
@NoArgsConstructor
@Document(indexName = "articles")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class ArticleElastic {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String name;
                                    
    @Field(type = Text)
    private String description;

    @Field(type = FieldType.Double)
    private Double price;

    @Field(type = FieldType.Long)
    private Long jpaId;

    public ArticleElastic(String name, String description, Double price, Long id) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.jpaId = id;
    }
}
