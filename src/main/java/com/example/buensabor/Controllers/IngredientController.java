package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.Ingredient;
import com.example.buensabor.Services.Impl.IngredientServiceImpl;
import com.example.buensabor.Services.Impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/ingredient")
public class IngredientController extends BaseControllerImpl<Ingredient, IngredientServiceImpl>{

    public IngredientController(IngredientServiceImpl service) {
        super(service);
    }

    @GetMapping("/nameAndState")
    public ResponseEntity<?> getByNameAndState(@RequestParam String name,@RequestParam String state){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getByNameAndState(name,state));
       }
        catch (Exception e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
       }
    }
}
