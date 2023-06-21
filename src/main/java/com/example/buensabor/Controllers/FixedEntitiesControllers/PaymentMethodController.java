package com.example.buensabor.Controllers.FixedEntitiesControllers;

import com.example.buensabor.Controllers.BaseControllerImpl;
import com.example.buensabor.Models.FixedEntities.DeliveryMethod;
import com.example.buensabor.Models.FixedEntities.PaymentMethod;
import com.example.buensabor.Services.Impl.DeliveryMethodServiceImpl;
import com.example.buensabor.Services.Impl.PaymentMethodServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/paymentmethod")
public class PaymentMethodController extends BaseControllerImpl<PaymentMethod, PaymentMethodServiceImpl> {

    public PaymentMethodController(PaymentMethodServiceImpl service) {
        super(service);
    }
}
