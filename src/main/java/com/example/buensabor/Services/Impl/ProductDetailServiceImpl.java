package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.Entity.ProductDetail;
import com.example.buensabor.Repositories.ProductDetailRepository;
import com.example.buensabor.Services.ProductDetailService;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl extends BaseServiceImpl<ProductDetail,Long> implements ProductDetailService {

    private ProductDetailRepository productDetailRepository;

    public ProductDetailServiceImpl(ProductDetailRepository productDetailRepository) {
        super(productDetailRepository);
        this.productDetailRepository = productDetailRepository;
    }

}
