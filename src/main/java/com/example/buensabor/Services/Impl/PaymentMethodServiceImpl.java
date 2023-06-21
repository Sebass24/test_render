package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.FixedEntities.PaymentMethod;
import com.example.buensabor.Repositories.PaymentMethodRepository;
import com.example.buensabor.Services.PaymentMethodService;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodServiceImpl extends BaseServiceImpl<PaymentMethod,Long> implements PaymentMethodService {

    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        super(paymentMethodRepository);
        this.paymentMethodRepository = paymentMethodRepository;
    }

}
