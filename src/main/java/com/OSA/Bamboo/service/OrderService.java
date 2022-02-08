package com.OSA.Bamboo.service;

import com.OSA.Bamboo.model.BuyerOrder;
import com.OSA.Bamboo.model.OrderedArticle;
import com.OSA.Bamboo.web.dtoElastic.OrderElasticDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    BuyerOrder saveBuyerOrder(BuyerOrder buyerOrder);

    BuyerOrder findById(Long id);

    OrderedArticle saveOrderedArticle(OrderedArticle orderedArticle);

    List<BuyerOrder> getBuyerOrders(String username);

    List<OrderElasticDto> getOrderElastic(String comment);

    List<BuyerOrder> getSellerComments(String username);

    Optional<Double> getSellerGrade(String username);

    List<BuyerOrder> getOrderByGrade(int min, int max);
}
