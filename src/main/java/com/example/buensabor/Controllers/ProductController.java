package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.Product;
import com.example.buensabor.Services.Impl.ImageServiceImpl;
import com.example.buensabor.Services.Impl.ProductServiceImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/product")
public class ProductController extends BaseControllerImpl<Product, ProductServiceImpl>{
    private ImageServiceImpl imageService;

    public ProductController(ProductServiceImpl service) {
        super(service);
    }

    @PostMapping(value = "/save", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> save(@RequestPart("Product") Product entity,@RequestPart(value = "Image",required = false) MultipartFile multipartFile){
        try {

            return ResponseEntity.status(HttpStatus.OK).body(service.save(entity,multipartFile));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @PutMapping(value="/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> update(@RequestPart("Product") Product entity,@RequestPart(value = "Image",required = false) MultipartFile multipartFile){
        try {
            if (multipartFile != null){
                return ResponseEntity.status(HttpStatus.OK).body(service.update(entity,multipartFile));
            }else
                return ResponseEntity.status(HttpStatus.OK).body(service.update(entity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getByCategory(@PathVariable String category){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getByCategory(category));
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

    @GetMapping("/ranking/{category}")
    public ResponseEntity<?> getProductsRanking(@PathVariable String category){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getProductRanking(category));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("/random/{quantity}")
    public ResponseEntity<?> getRandom(@PathVariable int quantity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getRandom(quantity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
    @GetMapping("/topProducts/{category}")
    public ResponseEntity<?> getTopProducts(@PathVariable String category, @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getTopProducts(category,startDate,endDate));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }



}
