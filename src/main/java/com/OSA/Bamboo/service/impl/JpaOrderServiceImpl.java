package com.OSA.Bamboo.service.impl;

import com.OSA.Bamboo.elastic.OrderElasticRepo;
import com.OSA.Bamboo.model.BuyerOrder;
import com.OSA.Bamboo.model.OrderedArticle;
import com.OSA.Bamboo.model.elastic.OrderElastic;
import com.OSA.Bamboo.repository.BuyerOrderRepo;
import com.OSA.Bamboo.repository.OrderedArticleRepo;
import com.OSA.Bamboo.service.OrderService;
import com.OSA.Bamboo.web.dtoElastic.OrderElasticDto;
import com.OSA.Bamboo.web.elasticConverter.OrderElasticConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaOrderServiceImpl implements OrderService {

    @Autowired
    private BuyerOrderRepo buyerOrderRepo;

    @Autowired
    private OrderedArticleRepo orderedArticleRepo;

    @Autowired
    private OrderElasticRepo orderElasticRepo;

    @Autowired
    private OrderElasticConverter orderElasticConverter;

    @Override
    public BuyerOrder saveBuyerOrder(BuyerOrder buyerOrder) {
        if (buyerOrder.isDelivered()) {
            OrderElastic orderElastic = new OrderElastic(
                    buyerOrder.getId(),
                    buyerOrder.getHourlyRate(),
                    buyerOrder.getGrade(),
                    buyerOrder.getComment()
            );

            if (buyerOrder.isAnonymousComment()) {
                orderElastic.setUser("Anonymous");
            } else {
                orderElastic.setUser(buyerOrder.getUser());
            }

            orderElasticRepo.save(orderElastic);
        }
        return buyerOrderRepo.save(buyerOrder);
    }

    @Override
    public List<OrderElasticDto> getOrderElastic(String comment) {
        return orderElasticConverter
                .listToDto(
                    orderElasticRepo.findOrderElasticByComment(comment)
                );
    }

    @Override
    public BuyerOrder findById(Long id) {
        return buyerOrderRepo.findById(id).get();
    }

    @Override
    public OrderedArticle saveOrderedArticle(OrderedArticle orderedArticle) {
        return orderedArticleRepo.save(orderedArticle);
    }

    @Override
    public List<BuyerOrder> getBuyerOrders(String username) {
        return buyerOrderRepo.findNotDeliveredOrdersByUsername(username);
    }

    @Override
    public List<BuyerOrder> getSellerComments(String username) {
        return buyerOrderRepo.findSellerComments(username);
    }

    @Override
    public Optional<Double> getSellerGrade(String username) {
        return buyerOrderRepo.sellerGrade(username);
    }
}
