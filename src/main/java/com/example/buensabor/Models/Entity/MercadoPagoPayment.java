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
@Table(name="mp_payment")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoPayment extends Base{
    private String paymentId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
