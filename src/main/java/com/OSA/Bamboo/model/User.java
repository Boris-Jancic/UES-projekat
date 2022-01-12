package com.OSA.Bamboo.model;

import com.OSA.Bamboo.model.enums.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = false, nullable = false)
    private String password;

    @Column(unique = false, nullable = false)
    private String name;

    @Column(unique = false, nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean blocked;

    public User(String name, String lastName, String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}

