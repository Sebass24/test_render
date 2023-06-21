package com.example.buensabor.Models.DTOs;

import com.example.buensabor.Models.FixedEntities.Location;
import jakarta.persistence.OneToOne;

public record AddressDTO(String street,
                         String number,
                         Location location) {
}
