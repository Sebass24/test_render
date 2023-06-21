package com.example.buensabor.Controllers;

import com.example.buensabor.Models.Entity.Base;
import com.example.buensabor.Services.Impl.BaseServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

public abstract class BaseControllerImpl<T extends Base, S extends BaseServiceImpl<T,Long>> implements BaseController<T, Long> {

    protected final S service;

    public BaseControllerImpl(S service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllActive(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAllActive());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("all")
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody T entity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(entity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody T entity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.update(entity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\":\"Eliminado Correctamente\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
}
