package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.Order;
import com.example.buensabor.Services.Impl.OrderServiceImpl;
import com.example.buensabor.Services.Impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/order")
public class OrderController extends BaseControllerImpl<Order, OrderServiceImpl>{

    public OrderController(OrderServiceImpl service) {
        super(service);
    }

    @GetMapping("byStatus/{status}")
    public ResponseEntity<?> getByStatus(@PathVariable String status){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getByStatus(status));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("byStatusAndID")
    public ResponseEntity<?> getByStatusAndId(@RequestParam String status,@RequestParam Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getByStatusAndName(status,id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("byUserID/{id}")
    public ResponseEntity<?> getByUserId(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getOrdersByUserId(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @PutMapping("changeStatus/{orderId}/{statusId}")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @PathVariable Long statusId){
        try {
            service.changeStatus(orderId,statusId);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mesage\":\"Estado actualizado\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @PutMapping("paiOrder/{orderId}")
    public ResponseEntity<?> setOrderPaid(@PathVariable Long orderId){
        try {
            service.setOrderPaid(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mesage\":\"Orden pagada, y factura enviada por mail\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @PutMapping("add10/{orderId}")
    public ResponseEntity<?> add10Min(@PathVariable Long orderId){
        try {
            service.addMinutesToOrder(orderId);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mesage\":\"Se agregaron 10 min\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
}
