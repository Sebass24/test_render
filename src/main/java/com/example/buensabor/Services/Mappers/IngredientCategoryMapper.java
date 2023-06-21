package com.example.buensabor.Services.Mappers;

import com.example.buensabor.Models.DTOs.IngredientCategoryDTO;
import com.example.buensabor.Models.Entity.IngredientCategory;

import java.util.function.Function;

public class IngredientCategoryMapper implements Function<IngredientCategory, IngredientCategoryDTO> {
    @Override
    public IngredientCategoryDTO apply(IngredientCategory ingredientCategory) {
        return new IngredientCategoryDTO(
                ingredientCategory.getName(), //<-- sospechoso no se si funcione
                ingredientCategory.getParentCategory()
        );
    }
}
