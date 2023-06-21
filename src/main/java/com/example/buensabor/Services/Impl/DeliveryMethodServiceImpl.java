package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.FixedEntities.DeliveryMethod;
import com.example.buensabor.Repositories.DeliveryMethodRepository;
import com.example.buensabor.Services.DeliveryMethodService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryMethodServiceImpl extends BaseServiceImpl<DeliveryMethod,Long> implements DeliveryMethodService {

    private DeliveryMethodRepository deliveryMethodRepository;

    public DeliveryMethodServiceImpl(DeliveryMethodRepository deliveryMethodRepository) {
        super(deliveryMethodRepository);
        this.deliveryMethodRepository = deliveryMethodRepository;
    }

}
