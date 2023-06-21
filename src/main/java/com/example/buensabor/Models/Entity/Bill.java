package com.example.buensabor.Models.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bill")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class Bill extends Base{
    private long number;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private boolean cancelled;

}
