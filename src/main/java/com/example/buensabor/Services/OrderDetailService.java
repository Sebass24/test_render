package com.example.buensabor.Services;

import com.example.buensabor.Models.Entity.OrderDetail;
import com.example.buensabor.Models.FixedEntities.OrderStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailService extends BaseService<OrderDetail,Long> {

    int countOrdersDetailByProduct(Long productId);
    List<OrderDetail> getOrdersDetailByOrder(List<Long> ordersIds);
}
