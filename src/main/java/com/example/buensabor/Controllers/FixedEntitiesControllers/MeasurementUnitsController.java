package com.example.buensabor.Controllers.FixedEntitiesControllers;

import com.example.buensabor.Models.FixedEntities.MeasurementUnit;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/enum/units")
public class MeasurementUnitsController {
    @GetMapping("")
    public ResponseEntity<?> getAllEnums() {
        try {
            MeasurementUnit[] enums = MeasurementUnit.values();
            return ResponseEntity.status(HttpStatus.OK).body(Arrays.asList(enums));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
}
