package com.OSA.Bamboo.model.elastic;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
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
    private String user;

    public OrderElastic(Long id, LocalDate hourlyRate, int grade, String comment) {
        this.id = id;
        this.hourlyRate = hourlyRate;
        this.grade = grade;
        this.comment = comment;
    }
}