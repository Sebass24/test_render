package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.Recipe;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends BaseRepository<Recipe,Long> {

}
