package com.example.buensabor.Services.Mappers;

import com.example.buensabor.Models.DTOs.AddressDTO;
import com.example.buensabor.Models.Entity.Address;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AddressMapper implements Function<Address, AddressDTO> {


    @Override
    public AddressDTO apply(Address address) {

        return new AddressDTO(
                address.getStreet(),
                address.getNumber(),
                address.getLocation());
    }
}
