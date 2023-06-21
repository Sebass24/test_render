package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.Address;
import com.example.buensabor.Models.Entity.CreditNote;
import com.example.buensabor.Services.Impl.AddressServiceImpl;
import com.example.buensabor.Services.Impl.CreditNoteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/address")
public class AddressController extends BaseControllerImpl<Address, AddressServiceImpl>{

    public AddressController(AddressServiceImpl service) {
        super(service);
    }

}
