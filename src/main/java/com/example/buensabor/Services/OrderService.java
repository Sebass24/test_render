package com.example.buensabor.Services;

import com.example.buensabor.Models.Entity.Order;
import com.example.buensabor.Models.Entity.Product;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface OrderService extends BaseService<Order,Long> {
    //Agregamos todos los metodos que solo pertenecen a User, lo agregamos a la interfaz para mantener el orden

    List<Order> getOrdersByDates(Date from, Date since);
    void changeStatus(Long orderId,Long newOrderStatusId);
    public List<Order> getByStatus(String status);
    public Double getCost(Order order);
    public Double getGain(Order order);
    public Double getIncome(Order order);
    public List<Order> getByStatusAndName(String status, Long id);
    public void setOrderPaid(Long orderId);

}
