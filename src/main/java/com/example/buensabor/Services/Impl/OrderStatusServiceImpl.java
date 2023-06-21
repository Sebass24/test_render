package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.FixedEntities.OrderStatus;
import com.example.buensabor.Repositories.OrderStatusRepository;
import com.example.buensabor.Services.OrderStatusService;
import org.springframework.stereotype.Service;

@Service
public class OrderStatusServiceImpl extends BaseServiceImpl<OrderStatus,Long> implements OrderStatusService {

    private OrderStatusRepository orderStatusRepository;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        super(orderStatusRepository);
        this.orderStatusRepository = orderStatusRepository;
    }

}
