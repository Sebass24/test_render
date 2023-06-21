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
public interface IngredientRepository extends BaseRepository<Ingredient,Long> {

   @Query("select i from Ingredient i where i.deleted = false and (i.name like %:name%   " +
            " and ((:estado = 'OPTIMO' AND i.currentStock > i.minimumStock * 1.2)" +
            " or (:estado = 'PEDIR' AND i.currentStock <= i.minimumStock * 1.2 AND i.currentStock > i.minimumStock) " +
            "or(:estado = 'FALTANTE' AND i.currentStock < i.minimumStock)))")
    List<Ingredient> getByNameAndState(@Param("name") String name,@Param("estado") String estado);

}
