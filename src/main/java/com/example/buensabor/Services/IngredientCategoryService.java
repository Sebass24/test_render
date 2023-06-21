package com.example.buensabor.Services;

import com.example.buensabor.Exceptions.RepositoryException;
import com.example.buensabor.Models.Entity.IngredientCategory;
import com.example.buensabor.Models.Entity.Product;

import java.util.List;

public interface IngredientCategoryService extends BaseService<IngredientCategory,Long>{
    List<IngredientCategory> getCategoryChildren(Long categoryId) throws RepositoryException;

    public List<IngredientCategory> getByName(String name);
}
