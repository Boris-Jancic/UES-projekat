package com.OSA.Bamboo.model.elastic;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

@Data
@Builder
@Document(indexName = "orders")
@Setting(settingPath = "/analyzers/serbianAnalyzer.json")
public class OrderElastic {
    @Id
    private Long id;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    private LocalDate hourlyRate;

    @Field(type = FieldType.Integer)
    private int grade;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Keyword)
    private String username;
}