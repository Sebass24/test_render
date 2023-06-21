package com.example.buensabor.Controllers.FixedEntitiesControllers;

import com.example.buensabor.Controllers.BaseControllerImpl;
import com.example.buensabor.Models.FixedEntities.DeliveryMethod;
import com.example.buensabor.Services.Impl.DeliveryMethodServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/deliverymethod")
public class DeliveryMethodController extends BaseControllerImpl<DeliveryMethod, DeliveryMethodServiceImpl> {

    public DeliveryMethodController(DeliveryMethodServiceImpl service) {
        super(service);
    }
}
