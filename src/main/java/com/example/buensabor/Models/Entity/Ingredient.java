package com.example.buensabor.Models.Entity;

import com.example.buensabor.Models.FixedEntities.MeasurementUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="ingredient")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class Ingredient extends Base{
    private String name;

    @OneToOne
    @JoinColumn(name = "category_id")
    private IngredientCategory ingredientCategory;

    private Double minimumStock;
    private Double currentStock;
    private Double costPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurementUnit", nullable = false)
    private MeasurementUnit measurementUnit;

    public void addStock(double quantity){
        this.currentStock += quantity;
    }

    public void decrementStock(double quantity){
        this.currentStock = this.currentStock - quantity;
    }
}
