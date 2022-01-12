package com.OSA.Bamboo.service.impl;

import com.OSA.Bamboo.elastic.OrderElasticRepo;
import com.OSA.Bamboo.model.BuyerOrder;
import com.OSA.Bamboo.model.OrderedArticle;
import com.OSA.Bamboo.model.elastic.OrderElastic;
import com.OSA.Bamboo.repository.BuyerOrderRepo;
import com.OSA.Bamboo.repository.OrderedArticleRepo;
import com.OSA.Bamboo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public BuyerOrder saveBuyerOrder(BuyerOrder buyerOrder) {
        if (buyerOrder.isDelivered()) {
            OrderElastic orderElastic = new OrderElastic(
                    buyerOrder.getHourlyRate(),
                    buyerOrder.getGrade(),
                    buyerOrder.getComment()
            );

            if (buyerOrder.isAnonymousComment()) {
                System.out.println("ANONIMAN ANONIMAN");
                orderElastic.setUser("Anonymous");
            } else {
                System.out.println("ANONIMAN NIJE ANONIMAN NIJE ");
                orderElastic.setUser(buyerOrder.getUser());
            }
            System.out.println(buyerOrder);
            System.out.println(orderElastic);

            orderElasticRepo.save(orderElastic);
        }
        return buyerOrderRepo.save(buyerOrder);
    }

    @Override
    public List<OrderElastic> getOrderElastic(String comment) {
        return orderElasticRepo.findByComment(comment, PageRequest.of(0, 50)).getContent();
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
