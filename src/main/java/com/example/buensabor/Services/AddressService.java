package com.example.buensabor.Services;

import com.example.buensabor.Models.Entity.Address;

import java.util.List;


public interface AddressService extends BaseService<Address,Long> {
    List<Address> getAddressesbyUser(Long id);
}
