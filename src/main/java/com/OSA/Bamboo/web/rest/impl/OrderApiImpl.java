package com.OSA.Bamboo.web.rest.impl;

import com.OSA.Bamboo.model.BuyerOrder;
import com.OSA.Bamboo.model.OrderedArticle;
import com.OSA.Bamboo.model.elastic.OrderElastic;
import com.OSA.Bamboo.service.impl.JpaOrderServiceImpl;
import com.OSA.Bamboo.web.converter.BuyerOrderToDto;
import com.OSA.Bamboo.web.converter.DtoToBuyerOrder;
import com.OSA.Bamboo.web.converter.DtoToOrderedArticle;
import com.OSA.Bamboo.web.dto.BuyerOrderDto;
import com.OSA.Bamboo.web.dto.OrderedArticleDto;
import com.OSA.Bamboo.web.rest.OrderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class OrderApiImpl implements OrderApi {

    @Autowired
    private JpaOrderServiceImpl orderService;

    @Autowired
    private DtoToOrderedArticle toEntityOrderA;

    @Autowired
    private DtoToBuyerOrder toEntityBuyerO;

    @Autowired
    private BuyerOrderToDto toDtoBo;

    private Long orderId = 1L;

    @Override
    public ResponseEntity postOrder(@Valid BuyerOrderDto dto) {
        BuyerOrder buyerOrder = toEntityBuyerO.convert(dto);
        System.out.println(buyerOrder);
        if (buyerOrder != null) {
            orderService.saveBuyerOrder(buyerOrder);
            orderId = buyerOrder.getId();
            return new ResponseEntity<>(buyerOrder, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity postOrderedArticle(@Valid OrderedArticleDto dto) {
        OrderedArticle orderedArticle = toEntityOrderA.convert(dto);
        if (orderedArticle != null) {
            orderedArticle.setOrderId(orderId);
            orderService.saveOrderedArticle(orderedArticle);
            return new ResponseEntity<>(orderedArticle, HttpStatus.OK);
        }
        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity sellerComments(String username) throws IOException {
        List<BuyerOrderDto> orders = toDtoBo.convert(orderService.getSellerComments(username));
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Override
    public ResponseEntity commentsElastic(String comment) {
        return new ResponseEntity<>(orderService.getOrderElastic(comment), HttpStatus.OK);
    }

    @Override
    public ResponseEntity sellerGrade(String username) {
        Optional<Double> grade = orderService.getSellerGrade(username);
        if (grade.isPresent()) {
            return new ResponseEntity<>(grade.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>("This seller has not been rated yet", HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity getOrders(String username) {
        List<BuyerOrder> buyerOrders = orderService.getBuyerOrders(username);
        if (!buyerOrders.isEmpty())
            return new ResponseEntity<>(buyerOrders, HttpStatus.OK);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity updateOrder(BuyerOrderDto dto) {
        BuyerOrder buyerOrder = toEntityBuyerO.convert(dto);
        System.out.println(buyerOrder);
        System.out.println(dto);
        if (buyerOrder != null)
            return new ResponseEntity<>(orderService.saveBuyerOrder(buyerOrder), HttpStatus.ACCEPTED);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
}
