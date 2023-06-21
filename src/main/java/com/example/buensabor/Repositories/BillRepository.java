package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.Bill;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends BaseRepository<Bill,Long> {

        @Query("select  MONTHNAME(o.date) AS month, sum(pd.ingredient.costPrice * pd.quantity) as totalRevenues from Order o " +
                "Join OrderDetail od " +
                "Join ProductDetail pd " +
                "where od.order.id = o.id " +
                "And pd.product.id = od.product.id " +
                "AND (:startDate IS NULL OR o.date >= :startDate) " +
               "AND (:endDate IS NULL OR o.date <= :endDate) " +
                "group by month"
        )
       List<Object> getBillingStatisticsCosts (@Param("startDate") Date startDate,
                                               @Param("endDate") Date endDate);



        @Query( "select MONTHNAME(o.date) AS month, sum(o.total) as totalRevenues from Order o " +
                "where (:startDate IS NULL OR o.date >= :startDate) " +
                "AND (:endDate IS NULL OR o.date <= :endDate) " +
                "group by month "
        )
        List<Object> getBillingStatistics (@Param("startDate") Date startDate,
                                    @Param("endDate") Date endDate);

        @Query("SELECT b FROM Bill b WHERE b.order.id = :orderId")
        Bill findByOrderId(@Param("orderId") Long orderId);

}
