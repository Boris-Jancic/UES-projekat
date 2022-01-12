package com.OSA.Bamboo.web.rest.impl;


import com.OSA.Bamboo.model.Discount;
import com.OSA.Bamboo.service.DiscountService;
import com.OSA.Bamboo.web.converter.DtoToDiscount;
import com.OSA.Bamboo.web.dto.DiscountDto;
import com.OSA.Bamboo.web.rest.DiscountApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class DiscountApiImpl implements DiscountApi {

    @Autowired
    private DtoToDiscount toEntity;

    @Autowired
    private DiscountService discountService;

    @Override
    public ResponseEntity postDiscount(@Valid DiscountDto dto) {
        Discount discount = toEntity.convert(dto);
        System.out.println(discount);
        if (discount != null) {
            discountService.save(discount);
            return new ResponseEntity<>(discount, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity postDiscount(@Valid Discount discount) {
        System.out.println(discount);
        if (discount != null) {
            discountService.save(discount);
            return new ResponseEntity<>(discount, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity getCurrentDiscounts(Long id) {
        List<Discount> discounts = discountService.getSellerDiscounts(id);
        return new ResponseEntity<>(discounts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteDiscount(Long id) {
        discountService.deleteDiscount(id);
        return new ResponseEntity<>("Discount deleted", HttpStatus.NO_CONTENT);
    }
}
