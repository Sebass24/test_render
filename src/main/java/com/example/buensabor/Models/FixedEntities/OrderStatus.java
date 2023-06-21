package com.example.buensabor.Models.FixedEntities;

import com.example.buensabor.Models.Entity.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="order_status")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus extends Base {
    private String description;
}
