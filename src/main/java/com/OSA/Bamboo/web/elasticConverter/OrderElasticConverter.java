package com.OSA.Bamboo.web.elasticConverter;

import com.OSA.Bamboo.model.BuyerOrder;
import com.OSA.Bamboo.model.elastic.ArticleElastic;
import com.OSA.Bamboo.model.elastic.OrderElastic;
import com.OSA.Bamboo.web.dto.BuyerOrderDto;
import com.OSA.Bamboo.web.dtoElastic.ArticleElasticDto;
import com.OSA.Bamboo.web.dtoElastic.OrderElasticDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderElasticConverter {

    @Autowired
    private ModelMapper modelMapper;

    public OrderElasticDto toDto(OrderElastic entity) {
        return modelMapper.map(entity, OrderElasticDto.class);
    }

    public BuyerOrderDto toDto(BuyerOrder entity) {
        return modelMapper.map(entity, BuyerOrderDto.class);
    }

    public OrderElastic toEntity(OrderElasticDto dto) throws ParseException {
        return modelMapper.map(dto, OrderElastic.class);
    }

    public List<OrderElasticDto> listToDto(List<OrderElastic> entities){
        List<OrderElasticDto> dtos = new ArrayList<>();
        for (OrderElastic a : entities) {
            dtos.add(toDto(a));
        }
        return dtos;
    }

    public List<OrderElastic> listToEntity(List<OrderElasticDto> dtos) throws ParseException {
        List<OrderElastic> orders = new ArrayList<>();
        for (OrderElasticDto s : dtos) {
            orders.add(toEntity(s));
        }
        return orders;
    }

    public List<BuyerOrderDto> convert(List<BuyerOrder> orders) {
        List<BuyerOrderDto> dtos = new ArrayList<>();
        for (BuyerOrder bo : orders) {
            if (bo.isAnonymousComment()) bo.setUsername("Anonymous");
            dtos.add(toDto(bo));
        }
        return dtos;
    }

    public static OrderElastic mapModel(OrderElasticDto dto) {
        return OrderElastic.builder()
                .hourlyRate(dto.getHourlyRate())
                .grade(dto.getGrade())
                .comment(dto.getComment())
                .username(dto.getUsername())
                .build();
    }

    public static OrderElasticDto mapResponseDto(OrderElastic orderElastic) {
        return OrderElasticDto.builder()
                .hourlyRate(orderElastic.getHourlyRate())
                .grade(orderElastic.getGrade())
                .comment(orderElastic.getComment())
                .username(orderElastic.getUsername())
                .build();
    }

    public static List<OrderElasticDto> mapDtosFromSearchHit(SearchHits<OrderElastic> searchHits) {
        return searchHits
                .map(order -> mapResponseDto(order.getContent()))
                .toList();
    }
}
