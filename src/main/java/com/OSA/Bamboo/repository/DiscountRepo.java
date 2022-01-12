package com.OSA.Bamboo.repository;

import com.OSA.Bamboo.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface DiscountRepo extends JpaRepository<Discount, Long> {
    @Query(value = "SELECT dis FROM Discount dis " +
            "WHERE CURRENT_DATE between dis.fromDate AND dis.tillDate " +
            "AND dis.seller.id = ?1")
    List<Discount> getActualDiscounts(Long id);

    @Query(value = "SELECT dis FROM Discount dis WHERE dis.article.id = ?1")
    Optional<Discount> findDiscountByArticleId(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Discount WHERE article.id = ?1")
    void deleteByArticleId(Long articleId);
}