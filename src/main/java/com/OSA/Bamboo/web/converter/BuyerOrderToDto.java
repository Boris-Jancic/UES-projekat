package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.BuyerOrder;
import com.OSA.Bamboo.web.dto.BuyerOrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BuyerOrderToDto implements Converter<BuyerOrder, BuyerOrderDto> {
    @Override
    public BuyerOrderDto convert(BuyerOrder buyerOrder) {
        BuyerOrderDto dto = new BuyerOrderDto();

        dto.setId(buyerOrder.getId());
        dto.setComment(buyerOrder.getComment());
        dto.setAnonymousComment(buyerOrder.isAnonymousComment());
        dto.setArchivedComment(buyerOrder.isArchivedComment());
        dto.setDelivered(buyerOrder.isDelivered());
        dto.setGrade(buyerOrder.getGrade());
        dto.setHourlyRate(buyerOrder.getHourlyRate().toString());
        dto.setUsername(buyerOrder.getUsername());

        return dto;
    }

    public List<BuyerOrderDto> convert(List<BuyerOrder> buyerOrders) throws IOException {
        List<BuyerOrderDto> retVal = new ArrayList<>();

        for (BuyerOrder bo : buyerOrders) {
            BuyerOrderDto dto = this.convert(bo);
            retVal.add(dto);
        }

        return retVal;
    }
}
