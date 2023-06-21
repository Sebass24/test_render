package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.Entity.OrderDetail;
import com.example.buensabor.Models.Entity.Product;
import com.example.buensabor.Models.FixedEntities.OrderStatus;
import com.example.buensabor.Repositories.OrderDetailRepository;
import com.example.buensabor.Repositories.OrderStatusRepository;
import com.example.buensabor.Services.OrderDetailService;
import com.example.buensabor.Services.OrderStatusService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl extends BaseServiceImpl<OrderDetail,Long> implements OrderDetailService {

    private OrderDetailRepository orderDetailRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository) {
        super(orderDetailRepository);
        this.orderDetailRepository = orderDetailRepository;
    }


    @Override
    public int countOrdersDetailByProduct(Long productId) {
        return orderDetailRepository.countOrdersDetailByProduct(productId);
    }

    @Override
    public List<OrderDetail> getOrdersDetailByOrder(List<Long> ordersIds) {
        return orderDetailRepository.getOrdersDetailByOrder(ordersIds);
    }

    public Double getOrderDetailPrice(OrderDetail orderDetail){
        return orderDetail.getProduct().getSellPrice();
    }
}
