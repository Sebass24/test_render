package com.example.buensabor.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/hola")
public class SaludeAlMundoController {
    @GetMapping("")
    public ResponseEntity<?> getAllEnums() {

        try {
            return ResponseEntity.status(HttpStatus.OK).body("{\"saludo\":\"Hola mundo!!!\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
}
