package com.example.buensabor.Models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="ingredient_category")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class IngredientCategory extends Base{
    private String name;

    @ManyToOne
    private IngredientCategory parentCategory;

}
