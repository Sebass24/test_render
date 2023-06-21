package com.example.buensabor.Services.Impl;

import com.example.buensabor.Exceptions.ServiceException;
import com.example.buensabor.Models.Entity.Address;
import com.example.buensabor.Models.Entity.User;
import com.example.buensabor.Repositories.AddressRepository;
import com.example.buensabor.Services.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl extends BaseServiceImpl<Address,Long> implements AddressService {

    private AddressRepository addressRepository; // dependencies injection

    public AddressServiceImpl(AddressRepository addressRepository) {
        super(addressRepository);
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Address save(Address entity) throws ServiceException {
        try {
            entity = addressRepository.save(entity);
//            User user = userService.findById(entity.getUser().getId());
//            entity.setUser(user);
            return entity;
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Address> getAddressesbyUser(Long id) {
        return addressRepository.getAddressesByUser(id);
    }
}
