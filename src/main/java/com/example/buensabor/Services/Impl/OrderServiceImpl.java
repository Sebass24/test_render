package com.example.buensabor.Services.Impl;

import com.example.buensabor.Exceptions.ServiceException;
import com.example.buensabor.Models.Entity.*;
import com.example.buensabor.Models.FixedEntities.OrderStatus;
import com.example.buensabor.Repositories.OrderRepository;
import com.example.buensabor.Services.Email.MailService;
import com.example.buensabor.Services.OrderService;
import com.example.buensabor.Util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order,Long> implements OrderService {

    private OrderRepository orderRepository;
    private OrderDetailServiceImpl orderDetailService;
    private ProductServiceImpl productService;
    private IngredientServiceImpl ingredientService;
    private OrderStatusServiceImpl orderStatusService;
    private BillServiceImpl billService;
    private MailService mailService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailServiceImpl orderDetailService, ProductServiceImpl productService, IngredientServiceImpl ingredientService, OrderStatusServiceImpl orderStatusService, BillServiceImpl billService, MailService mailService) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
        this.productService = productService;
        this.ingredientService = ingredientService;
        this.orderStatusService = orderStatusService;
        this.billService = billService;
        this.mailService = mailService;
    }

    @Override
    @Transactional
    public Order save(Order entity) throws ServiceException {
        try {
            List<OrderDetail> od = entity.getOrderDetails();

            od.forEach(orderDetail -> orderDetail.setOrder(entity));
            Date now = new Date();
            entity.setDate(now);


            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);

            int minutesToAdd = calculateEstimatedTime(entity);
            calendar.add(Calendar.MINUTE, minutesToAdd);
            Date estimatedTime = calendar.getTime();

            entity.setEstimatedTime(estimatedTime);

            AtomicBoolean onlyDrinks = new AtomicBoolean(true);
            od.forEach(orderDetail -> {
                if(!orderDetail.getProduct().getProductCategory().getDescription().equals("Bebidas")){
                    onlyDrinks.set(false);
                }
            });
            if(onlyDrinks.get()){
                OrderStatus os = orderStatusService.findById(Long.valueOf(4));
                entity.setOrderStatus(os);
            }

            Order order = baseRepository.save(entity);
            return order;

        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Order update(Order entity) throws ServiceException {
        try {
            if (entity.getId() == null) {
                throw new ServiceException("La entidad a modificar debe contener un Id.");
            }

            List<OrderDetail> pd = entity.getOrderDetails();
            pd.forEach(orderDetail -> orderDetail.setOrder(entity));
            Order order = orderRepository.save(entity);

            return order;
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private void decrementIngredientStock(Order order){
        try{
            for (OrderDetail orderDetail : order.getOrderDetails())
            {
                Product product = productService.findById(orderDetail.getProduct().getId());
                for (ProductDetail productDetail : product.getProductDetails())
                {
                    ingredientService.decrementStock(
                            productDetail.getIngredient().getId(),
                            productDetail.getQuantity(),
                            productDetail.getMeasurementUnit());

                }
            }
        }catch (Exception e){
            System.out.println("Error al decrementar stock");
        }

    }

    private void incrementIngredientStock(Order order){
        try{
            for (OrderDetail orderDetail : order.getOrderDetails())
            {
                for (ProductDetail productDetail : orderDetail.getProduct().getProductDetails())
                {
                    ingredientService.incrementStock(
                            productDetail.getIngredient().getId(),
                            productDetail.getQuantity(),
                            productDetail.getMeasurementUnit());

                }
            }
        }catch (Exception e){

        }

    }

    @Override
    public List<Order> getOrdersByDates(Date from, Date since) {
        return orderRepository.getOrdersByDates(from,since);
    }

    @Override
    public void changeStatus(Long orderId, Long newOrderStatusId) {
        try{
            Order order = orderRepository.findById(orderId).get();
            if (order.getOrderStatus().getId() == newOrderStatusId)
                return;

            OrderStatus orderStatus = orderStatusService.findById(newOrderStatusId);
            if(orderStatus.getId() == 2)
                decrementIngredientStock(order);

            order.setOrderStatus(orderStatus);
            orderRepository.save(order);

            //if (order.get().getOrderStatus().getDescription().equalsIgnoreCase("Cancelado")){
                //incrementIngredientStock(order.get());
                //Evaluando si esto tiene sentido o no.
            //}
        }
        catch (Exception e){
            System.out.println("Error al cambiar estado de orden");
        }
    }

    @Override
    public List<Order> getByStatus(String status) {
        return orderRepository.getOrdersByStatus(status);
    }
    @Override
    public List<Order> getByStatusAndName(String status,Long id) {
        return orderRepository.getOrdersByStatusAndId(status, id);
    }

    @Override
    public Double getCost(Order order) {

        double cost = 0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            cost += productService.getProductCost(orderDetail.getProduct());
        }
        return cost;
    }

    @Override
    public Double getGain(Order order) {

        double cost = getCost(order);
        double income = getIncome(order);
        return (income - cost);
    }

    @Override
    public Double getIncome(Order order) {
        double income = 0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            income += orderDetailService.getOrderDetailPrice(orderDetail);
        }
        return  income;
    }

    @Override
    public void setOrderPaid(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setPaid(true);
        String userEmail = order.getUser().getUserEmail();
        try {
            if (billService.validateNonDuplicateBill(orderId)){
                Bill bill = new Bill(order.getId(), order, false);
                billService.save(bill);
                orderRepository.save(order);
                billService.generateBillByOrderId(order.getId());
                mailService.sendBill(userEmail);
            }
        }catch (Exception e){
            System.out.println("Error al guardar la factura");
        }finally {
            Util.deleteTemp();
        }
    }

    public int calculateEstimatedTime(Order order){
        long orderCookingTime = 0;

        for (OrderDetail od : order.getOrderDetails()) {
            orderCookingTime += od.getProduct().getCookingTime();
        }

        List<Order> ordersAtKitchen = orderRepository.getOrdersByStatus("En cocina");
        if (!ordersAtKitchen.isEmpty()) {
            List<Date> estimatedTimes = ordersAtKitchen.stream().map(Order::getEstimatedTime).collect(Collectors.toList());
            Date maxDate = Collections.max(estimatedTimes);
            Date now = new Date();

            LocalDateTime localDateTime1 = maxDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime localDateTime2 = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            Duration duration = Duration.between(localDateTime2, localDateTime1);
            long minutosDiferencia = duration.toMinutes();

            orderCookingTime += minutosDiferencia;
        }
        return (int) orderCookingTime + 10;
    }

    public List<Order> getOrdersByUserId(Long id){
        try{
            return orderRepository.getOrdersByUser(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void addMinutesToOrder(Long id) throws Exception{
        Order order = orderRepository.findById(id).get();

        if (order != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getEstimatedTime());

            // Sumar 10 minutos
            calendar.add(Calendar.MINUTE, 10);

            // Obtener la nueva fecha sumada
            Date addDate = calendar.getTime();

            order.setEstimatedTime(addDate);

            orderRepository.save(order);
        }else throw new Exception();
    }

}
