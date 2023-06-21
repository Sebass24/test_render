package com.example.buensabor.Services.Impl;

import com.example.buensabor.Exceptions.RepositoryException;
import com.example.buensabor.Models.Entity.IngredientCategory;
import com.example.buensabor.Models.Entity.Product;
import com.example.buensabor.Repositories.IngredientCategoryRepository;
import com.example.buensabor.Services.IngredientCategoryService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientCategoryServiceImpl extends BaseServiceImpl<IngredientCategory,Long> implements IngredientCategoryService {

    private IngredientCategoryRepository ingredientCategoryRepository;
    public IngredientCategoryServiceImpl(IngredientCategoryRepository ingredientCategoryRepository) {
        super(ingredientCategoryRepository);
        this.ingredientCategoryRepository = ingredientCategoryRepository;
    }

    @Override
    public List<IngredientCategory> getCategoryChildren(Long categoryId) throws RepositoryException{
        return ingredientCategoryRepository.getChildren(categoryId);
    }
    @Override
    public List<IngredientCategory> getByName(String name) {
        return ingredientCategoryRepository.getByName(name);
    }
}
