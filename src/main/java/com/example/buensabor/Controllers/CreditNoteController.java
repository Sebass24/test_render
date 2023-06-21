package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.CreditNote;
import com.example.buensabor.Services.Impl.CreditNoteServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/credit-note")
public class CreditNoteController extends BaseControllerImpl<CreditNote, CreditNoteServiceImpl>{

    public CreditNoteController(CreditNoteServiceImpl service) {
        super(service);
    }


}
