package com.OSA.Bamboo.service.impl;

import com.OSA.Bamboo.elastic.OrderElasticRepo;
import com.OSA.Bamboo.model.BuyerOrder;
import com.OSA.Bamboo.model.OrderedArticle;
import com.OSA.Bamboo.model.elastic.ArticleElastic;
import com.OSA.Bamboo.model.elastic.OrderElastic;
import com.OSA.Bamboo.repository.BuyerOrderRepo;
import com.OSA.Bamboo.repository.OrderedArticleRepo;
import com.OSA.Bamboo.service.OrderService;
import com.OSA.Bamboo.web.dtoElastic.OrderElasticDto;
import com.OSA.Bamboo.web.elasticConverter.ArticleElasticConverter;
import com.OSA.Bamboo.web.elasticConverter.OrderElasticConverter;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@Service
public class JpaOrderServiceImpl implements OrderService {

    @Autowired
    private ElasticsearchOperations elasticsearchTemplate;

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
            OrderElastic orderElastic = OrderElastic.builder()
                .hourlyRate(buyerOrder.getHourlyRate())
                .grade(buyerOrder.getGrade())
                .comment(buyerOrder.getComment())
                .username(buyerOrder.getUsername())
                .build();

            if (buyerOrder.isAnonymousComment()) {
                orderElastic.setUsername("Anonymous");
            } else {
                orderElastic.setUsername(buyerOrder.getUsername());
            }

            orderElasticRepo.save(orderElastic);
        }
        return buyerOrderRepo.save(buyerOrder);
    }

    @Override
    public List<OrderElasticDto> getOrderElastic(String comment) {
//      NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()  // PHRASE QUERY EXAMPLE
//                .withQuery(matchPhraseQuery("comment", comment).slop(3))
//                .build();
//      NativeSearchQuery searchQuery = new NativeSearchQueryBuilder() // FUZZY QUERY EXAMPLE
//                .withQuery(matchQuery("comment", comment)
//                    .operator(Operator.AND)
//                    .fuzziness(Fuzziness.TWO)
//                    .prefixLength(1))
//                .build();
//        SearchHits<OrderElastic> ordersHit = elasticsearchTemplate.search(searchQuery, OrderElastic.class);
//
//        return OrderElasticConverter.mapDtosFromSearchHit(ordersHit);

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

    @Override
    public List<BuyerOrder> getOrderByGrade(int min, int max) {
        return buyerOrderRepo.findBuyerOrderByGradeBetween(min, max);
    }
}
