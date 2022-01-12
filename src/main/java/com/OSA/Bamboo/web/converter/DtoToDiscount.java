package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.Discount;
import com.OSA.Bamboo.repository.UserRepo;
import com.OSA.Bamboo.service.ArticleService;
import com.OSA.Bamboo.web.dto.DiscountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToDiscount implements Converter<DiscountDto, Discount> {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Discount convert(DiscountDto dto) {
        Discount discount = new Discount();
        int per = Integer.parseInt(dto.getDiscountPercent());
        discount.setDiscountPercent(per);
        discount.setDescription(dto.getDescription());
        discount.setFromDate(dto.getFromDate());
        discount.setTillDate(dto.getTillDate());
        discount.setArticle(articleService.one(dto.getArticleId()).get());
        discount.setSeller(userRepo.findById(dto.getSellerId()).get());
        return discount;
    }
}
