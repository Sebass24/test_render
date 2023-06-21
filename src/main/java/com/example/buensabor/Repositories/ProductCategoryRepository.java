package com.example.buensabor.Repositories;

import com.example.buensabor.Models.FixedEntities.ProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends BaseRepository<ProductCategory,Long> {

    @Query("select pc from ProductCategory pc where pc.description like %:name% and pc.deleted = false")
    List<ProductCategory> getByName(@Param("name") String name);

}
