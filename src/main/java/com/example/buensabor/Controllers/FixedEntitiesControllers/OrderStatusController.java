package com.example.buensabor.Controllers.FixedEntitiesControllers;

import com.example.buensabor.Controllers.BaseControllerImpl;
import com.example.buensabor.Models.FixedEntities.DeliveryMethod;
import com.example.buensabor.Models.FixedEntities.OrderStatus;
import com.example.buensabor.Services.Impl.DeliveryMethodServiceImpl;
import com.example.buensabor.Services.Impl.OrderStatusServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/orderstatus")
public class OrderStatusController extends BaseControllerImpl<OrderStatus, OrderStatusServiceImpl> {

    public OrderStatusController(OrderStatusServiceImpl service) {
        super(service);
    }
}
