package com.example.buensabor.Services;

import com.example.buensabor.Models.FixedEntities.ProductCategory;

import java.util.List;

public interface ProductCategoryService extends BaseService<ProductCategory,Long> {
    public List<ProductCategory> getByName(String name);
}
