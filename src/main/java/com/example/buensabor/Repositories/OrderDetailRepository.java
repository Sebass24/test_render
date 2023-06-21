package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.OrderDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDetailRepository extends BaseRepository<OrderDetail,Long> {

    @Query("select COUNT(o) from OrderDetail o where o.product.id = :productId")
    int countOrdersDetailByProduct(@Param("productId") Long productId);

    @Query("select o from OrderDetail o where o.order.id in (:ordersIds)")
    List<OrderDetail> getOrdersDetailByOrder(@Param("ordersIds") List<Long> ordersIds);
}
