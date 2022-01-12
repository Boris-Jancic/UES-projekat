package com.OSA.Bamboo.repository;

import com.OSA.Bamboo.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepo extends JpaRepository<Buyer, Long> {
    public Buyer findByUserId(Long id);
}
