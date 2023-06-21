package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.IngredientCategory;
import com.example.buensabor.Services.Impl.IngredientCategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/rubro")
public class IngredientCategoryController extends BaseControllerImpl<IngredientCategory, IngredientCategoryServiceImpl>{

    public IngredientCategoryController(IngredientCategoryServiceImpl service) {
        super(service);
    }

    @GetMapping("chindren/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getCategoryChildren(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getByName(name));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
}
