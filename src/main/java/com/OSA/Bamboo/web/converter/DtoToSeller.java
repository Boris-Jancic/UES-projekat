package com.OSA.Bamboo.web.converter;

import com.OSA.Bamboo.model.Seller;
import com.OSA.Bamboo.model.User;
import com.OSA.Bamboo.repository.UserRepo;
import com.OSA.Bamboo.web.dto.SellerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DtoToSeller implements Converter<SellerDto, Seller> {

    @Autowired
    private UserRepo userRepo;

    public DtoToSeller() {
    }

    @Override
    public Seller convert(SellerDto dto) {
        Seller seller = new Seller();
        User user = new User(dto.getName(), dto.getLastName(), dto.getUsername(), dto.getPassword());
        seller.setUser(user);
        seller.setSellerName(dto.getSellerName());
        seller.setAddress(dto.getAddress());
        seller.setEmail(dto.getEmail());
        seller.setSellingSince(LocalDate.now());
        return seller;
    }
}
