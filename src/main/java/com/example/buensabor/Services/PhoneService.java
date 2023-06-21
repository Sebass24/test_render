package com.example.buensabor.Services;

import com.example.buensabor.Models.Entity.Phone;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PhoneService extends BaseService<Phone,Long> {

    List<Phone> getPhonesByUser(Long id);
}
