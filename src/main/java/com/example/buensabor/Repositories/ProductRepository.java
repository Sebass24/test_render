package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product,Long> {

//    @Query("select o from Order o where o.de)
//    List<Product> search(@Param("filtro") String filtro);

    @Query("select p from Product p where p.productCategory.description like %:category% and p.deleted = false")
    List<Product> getByCategory(@Param("category") String category);

    @Query("select p from Product p where p.name like %:name% and p.deleted = false")
    List<Product> getByName(@Param("name") String name);

    @Query("select p from Product p where p.deleted = false and p.available = true")
    List<Product> getAvailable();

    @Query("select od.product, sum(od.quantity) as quantity from OrderDetail od " +
            "where od.product.productCategory.description like %:category% " +
            "AND (:startDate IS NULL OR od.order.date >= :startDate) " +
            "AND (:endDate IS NULL OR od.order.date <= :endDate)   " +
            "group by od.product.id order by quantity desc "
           )
    List<Object> getTopProducts(@Param("category") String category,@Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);


}
