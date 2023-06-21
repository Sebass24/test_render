package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.Entity.Recipe;
import com.example.buensabor.Models.FixedEntities.Role;
import com.example.buensabor.Repositories.RecipeRepository;
import com.example.buensabor.Repositories.RoleRepository;
import com.example.buensabor.Services.RecipeService;
import com.example.buensabor.Services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl extends BaseServiceImpl<Recipe,Long> implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        super(recipeRepository);
        this.recipeRepository = recipeRepository;
    }

}
