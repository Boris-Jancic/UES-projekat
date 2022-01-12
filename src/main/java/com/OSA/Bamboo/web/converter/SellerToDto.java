package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.Seller;
import com.OSA.Bamboo.web.dto.SellerDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SellerToDto implements Converter<Seller, SellerDto> {
    public SellerToDto() {
    }

    @Override
    public SellerDto convert(Seller seller) {
        SellerDto dto = new SellerDto();

        dto.setName(seller.getUser().getName());
        dto.setLastName(seller.getUser().getLastName());
        dto.setUsername(seller.getUser().getUsername());
        dto.setUsername(seller.getUser().getPassword());
        dto.setAddress(seller.getAddress());
        dto.setEmail(seller.getEmail());
        dto.setSellerName(seller.getSellerName());

        return dto;
    }

    public List<SellerDto> convert(List<Seller> sellers) throws IOException {
        List<SellerDto> retVal = new ArrayList<>();

        for (Seller s : sellers) {
            SellerDto dto = this.convert(s);
            retVal.add(dto);
        }

        return retVal;
    }
}
