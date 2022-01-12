package com.OSA.Bamboo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seller_id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private LocalDate sellingSince;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String sellerName;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
}
