package com.example.buensabor.Services.Mappers;

import com.example.buensabor.Models.DTOs.PhoneDTO;
import com.example.buensabor.Models.Entity.Phone;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PhoneMapper implements Function<Phone, PhoneDTO> {


    @Override
    public PhoneDTO apply(Phone phone) {

        return new PhoneDTO(
                phone.getNumber());
    }
}
