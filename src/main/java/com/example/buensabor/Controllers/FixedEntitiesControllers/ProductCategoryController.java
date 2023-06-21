package com.example.buensabor.Controllers.FixedEntitiesControllers;

import com.example.buensabor.Controllers.BaseControllerImpl;
import com.example.buensabor.Models.FixedEntities.DeliveryMethod;
import com.example.buensabor.Models.FixedEntities.ProductCategory;
import com.example.buensabor.Services.Impl.DeliveryMethodServiceImpl;
import com.example.buensabor.Services.Impl.ProductCategoryServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/category")
public class ProductCategoryController extends BaseControllerImpl<ProductCategory, ProductCategoryServiceImpl> {

    public ProductCategoryController(ProductCategoryServiceImpl service) {
        super(service);
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
