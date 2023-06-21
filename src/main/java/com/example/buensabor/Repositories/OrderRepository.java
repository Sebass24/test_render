package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.Ingredient;
import com.example.buensabor.Models.Entity.Order;
import com.example.buensabor.Models.Entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order,Long> {

    @Query("select o from Order o where o.date > :from and o.date < :since")
    List<Order> getOrdersByDates(@Param("from") Date from, @Param("since") Date since);

    @Query("select o from Order o where o.orderStatus.description = :status")
    List<Order> getOrdersByStatus(@Param("status") String status);

    @Query("select o from Order o where o.orderStatus.description = :status or :status = '' and o.id = :id")
    List<Order> getOrdersByStatusAndId(@Param("status") String status, @Param("id") Long id);

    @Query("select o from Order o where o.user.id = :id order by o.date desc")
    List<Order> getOrdersByUser(@Param("id") Long id);


}
