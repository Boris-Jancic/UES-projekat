package com.OSA.Bamboo.repository;

import com.OSA.Bamboo.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepo extends JpaRepository<Seller, Long> {
    public Seller findByUserId(Long id);
}
