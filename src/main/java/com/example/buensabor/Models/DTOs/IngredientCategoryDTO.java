package com.example.buensabor.Models.DTOs;

import com.example.buensabor.Models.Entity.IngredientCategory;

import java.util.List;

public record IngredientCategoryDTO(
        String name,
        IngredientCategory parentCategory
) {
}
