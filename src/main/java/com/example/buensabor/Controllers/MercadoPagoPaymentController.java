package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.MercadoPagoPayment;
import com.example.buensabor.Services.Impl.MercadoPagoPaymentServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/mp-pay")
public class MercadoPagoPaymentController extends BaseControllerImpl<MercadoPagoPayment, MercadoPagoPaymentServiceImpl>{

    public MercadoPagoPaymentController(MercadoPagoPaymentServiceImpl service) {
        super(service);
    }


}
