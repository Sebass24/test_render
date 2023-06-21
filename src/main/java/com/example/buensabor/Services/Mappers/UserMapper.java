package com.example.buensabor.Services.Mappers;

import com.example.buensabor.Models.DTOs.UserDTO;
import com.example.buensabor.Models.Entity.User;
import com.example.buensabor.Services.Impl.AddressServiceImpl;
import com.example.buensabor.Services.Impl.PhoneServiceImpl;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserMapper implements Function<User, UserDTO> {

    private AddressServiceImpl addressService;
    private PhoneServiceImpl phoneService;
    private AddressMapper addressMapper;
    private PhoneMapper phoneMapper;

    public UserMapper(AddressServiceImpl addressService, PhoneServiceImpl phoneService, AddressMapper addressMapper, PhoneMapper phoneMapper) {
        this.addressService = addressService;
        this.phoneService = phoneService;
        this.addressMapper = addressMapper;
        this.phoneMapper = phoneMapper;
    }

    @Override
    public UserDTO apply(User user) { //Usar mapStruct (libreria)

        return new UserDTO(
                user.getUserEmail(),
                user.getRole(),
                user.getName(),
                user.getLastName(),
                addressService.getAddressesbyUser(user.getId()).stream().map(addressMapper).collect(Collectors.toList()),
                phoneService.getPhonesByUser(user.getId()).stream().map(phoneMapper).collect(Collectors.toList())
        );
    }
}