package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.BuyerOrder;
import com.OSA.Bamboo.service.UserService;
import com.OSA.Bamboo.web.dto.BuyerOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DtoToBuyerOrder implements Converter<BuyerOrderDto, BuyerOrder> {

    @Autowired
    private UserService userService;

    @Override
    public BuyerOrder convert(BuyerOrderDto dto) {
        BuyerOrder buyerOrder = new BuyerOrder();

        buyerOrder.setId(dto.getId());
        buyerOrder.setComment(dto.getComment());
        buyerOrder.setAnonymousComment(dto.isAnonymousComment());
        buyerOrder.setArchivedComment(dto.isArchivedComment());
        buyerOrder.setDelivered(dto.isDelivered());
        buyerOrder.setGrade(dto.getGrade());
        buyerOrder.setHourlyRate(LocalDate.now());
        buyerOrder.setUser(dto.getUsername());

        return buyerOrder;
    }
}