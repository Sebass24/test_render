package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.Address;
import com.example.buensabor.Models.Entity.Phone;
import com.example.buensabor.Services.Impl.AddressServiceImpl;
import com.example.buensabor.Services.Impl.PhoneServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/phone")
public class PhoneController extends BaseControllerImpl<Phone, PhoneServiceImpl>{

    public PhoneController(PhoneServiceImpl service) {
        super(service);
    }




}
