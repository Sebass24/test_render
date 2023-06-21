package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.Entity.MercadoPagoPayment;
import com.example.buensabor.Repositories.MercadoPagoPaymentRepository;
import com.example.buensabor.Services.MercadoPagoPaymentService;
import org.springframework.stereotype.Service;

@Service
public class MercadoPagoPaymentServiceImpl extends BaseServiceImpl<MercadoPagoPayment,Long> implements MercadoPagoPaymentService {

    private MercadoPagoPaymentRepository mercadoPagoPaymentRepository;

    public MercadoPagoPaymentServiceImpl(MercadoPagoPaymentRepository mercadoPagoPaymentRepository) {
        super(mercadoPagoPaymentRepository);
        this.mercadoPagoPaymentRepository = mercadoPagoPaymentRepository;
    }

}
