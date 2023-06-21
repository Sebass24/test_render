package com.example.buensabor.Models.Entity;

import com.example.buensabor.Models.FixedEntities.DeliveryMethod;
import com.example.buensabor.Models.FixedEntities.OrderStatus;
import com.example.buensabor.Models.FixedEntities.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="new_order")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Base{

    @OneToOne
    private DeliveryMethod deliveryMethod;

    private Date date;

    @OneToOne
    private OrderStatus orderStatus;

    private Date estimatedTime;

    @OneToOne
    private PaymentMethod paymentMethod;

    private boolean paid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderDetail> orderDetails;

    private Double total;
    private Double discount;
    private String address;
    private String phone;

}
