package com.OSA.Bamboo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int discountPercent;

    private Date fromDate;

    private Date tillDate;

    private String description;

    @OneToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User seller;
}
