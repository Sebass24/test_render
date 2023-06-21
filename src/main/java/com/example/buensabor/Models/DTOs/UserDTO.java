package com.example.buensabor.Models.DTOs;

import com.example.buensabor.Models.Entity.Address;
import com.example.buensabor.Models.Entity.Phone;
import com.example.buensabor.Models.FixedEntities.Role;

import java.util.List;

public record UserDTO(
        String userEmail,
        Role role,
        String name,
        String lastName,
        List<AddressDTO> addresses,
        List<PhoneDTO> phones
) {
}
