package com.example.buensabor.Models.Entity;

import com.example.buensabor.Models.FixedEntities.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(name="product")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Base{

    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    private String shortDescription;

    @Column(name = "available", columnDefinition = "boolean default true")
    private boolean available;
    private int cookingTime;

    @OneToOne
    private ProductCategory productCategory;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private List<ProductDetail> productDetails;

    private Double sellPrice;

    @OneToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;
}
