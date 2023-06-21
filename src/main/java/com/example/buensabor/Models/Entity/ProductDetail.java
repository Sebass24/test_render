package com.example.buensabor.Models.Entity;

import com.example.buensabor.Models.FixedEntities.MeasurementUnit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Entity
@Table(name="product_detail")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail extends Base{

    @OneToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_unit", nullable = false)
    private MeasurementUnit measurementUnit;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;
}
