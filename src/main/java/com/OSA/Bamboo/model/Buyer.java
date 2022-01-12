package com.OSA.Bamboo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "buyer_id", unique = true, nullable = false)
    private Long id;

    private String address;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private User user;
}
