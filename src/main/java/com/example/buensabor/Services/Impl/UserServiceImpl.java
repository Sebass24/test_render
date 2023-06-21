package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.Entity.Address;
import com.example.buensabor.Models.Entity.Phone;
import com.example.buensabor.Models.Entity.User;
import com.example.buensabor.Repositories.AddressRepository;
import com.example.buensabor.Repositories.PhoneRepository;
import com.example.buensabor.Repositories.UserRepository;
import com.example.buensabor.Services.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {

    private UserRepository userRepository; // dependencies injection
    private AddressServiceImpl addressService;
    private PhoneServiceImpl phoneService;

    public UserServiceImpl(UserRepository userRepository, AddressRepository addressRepository, PhoneRepository phoneRepository, AddressServiceImpl addressService, PhoneServiceImpl phoneService) {
        super(userRepository);
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.phoneService = phoneService;
    }


    @Override
    public List<Address> getUserAddresses(Long userId) {
        List<Address> userAddresses = addressService.getAddressesbyUser(userId);
        return userAddresses;
    }

    @Override
    public List<Phone> getUserPhones(Long userId) {
        List<Phone> userPhones = phoneService.getPhonesByUser(userId);
        return userPhones;
    }

    @Override
    public List<Object> getTop5UsersOrders(int limit) {

        List<Object> users = userRepository.getTop5UsersOrders(PageRequest.of(0, limit));
        return users;
    }

    @Override
    public List<Object> getTop5UsersPrice(int limit) {
        List<Object> users = userRepository.getTop5UsersPrice(PageRequest.of(0, limit));
        return users;
    }

    @Override
    public List<Object> getUsersWithMostOrders(Date startDate, Date endDate, int Limit) {

        List<Object> users = userRepository.getUsersWithMostOrders(startDate,endDate,PageRequest.of(0, Limit));
        return users;
    }

    @Override
    public List<Object> getUsersWithMostPrice(Date startDate, Date endDate, int Limit) {

        List<Object> users = userRepository.getUsersWithMostPrice(startDate,endDate,PageRequest.of(0, Limit));
        return users;
    }

    @Override
    public User getUserByAuth0Id(String id) {
        return userRepository.getUserByAuth0Id(id);
    }

    @Override
    public List<User> getEmpleoyees(String rol, String name) {
        return userRepository.getEmpleoyees( rol,  name);
    }
    @Override
    public List<User> getClients(String name) {
        return userRepository.getClients(name);
    }
}
