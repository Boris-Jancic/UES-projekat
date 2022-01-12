package com.OSA.Bamboo.service;

import com.OSA.Bamboo.model.Discount;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DiscountService {
    Discount save(Discount discount);

    List<Discount> getSellerDiscounts(Long id);

    void deleteDiscount(Long id);
}
