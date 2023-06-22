package com.example.buensabor.Controllers;


import com.example.buensabor.Models.Entity.Review;
import com.example.buensabor.Models.Entity.User;
import com.example.buensabor.Models.FixedEntities.Role;
import com.example.buensabor.Services.Email.MailService;
import com.example.buensabor.Services.Impl.Auth0Service;
import com.example.buensabor.Services.Impl.RoleServiceImpl;
import com.example.buensabor.Services.Impl.UserServiceImpl;
import com.example.buensabor.Services.Mappers.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/user")
public class UserController extends BaseControllerImpl<User, UserServiceImpl>{

    private UserMapper userMapper;
    private Auth0Service auth0Service;
    private MailService mailService;
    private RoleServiceImpl roleService;
    public UserController(UserServiceImpl service, UserMapper userMapper, Auth0Service auth0Service, MailService mailService, RoleServiceImpl roleService) {
        super(service);
        this.userMapper = userMapper;
        this.auth0Service = auth0Service;
        this.mailService = mailService;
        this.roleService = roleService;
    }

    @Override
    @GetMapping("")
    public ResponseEntity<?> getAllActive(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAllActive().stream().map(userMapper).collect(Collectors.toList()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @Override
    @GetMapping("all")
    public ResponseEntity<?> getAll(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.findAll());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @PutMapping("")
    public ResponseEntity<?> update(@RequestBody User entity){
        try {
            if(entity.getRole().getAuth0RoleId() != null && entity.getAuth0Id() != null){
                auth0Service.assignRoleToUser(entity.getAuth0Id(),entity.getRole().getAuth0RoleId());
            }

            return ResponseEntity.status(HttpStatus.OK).body(service.update(entity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @Override
    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody User entity){
        try {
            if(entity.getRole() != null){
                auth0Service.assignRoleToUser(entity.getAuth0Id(),entity.getRole().getAuth0RoleId());
            }else{
                Role rolCliente = roleService.findById(Long.valueOf(2));//cliente
                entity.setRole(rolCliente);
                auth0Service.assignRoleToUser(entity.getAuth0Id(),rolCliente.getAuth0RoleId());
            }

            return ResponseEntity.status(HttpStatus.OK).body(service.save(entity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<?> createAsAdmin(@RequestBody User entity){
        try {

            if(entity != null)
                entity = auth0Service.createAuth0User(entity);

            return ResponseEntity.status(HttpStatus.OK).body(service.save(entity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @DeleteMapping("/deleteEmployee/{id}")
    public ResponseEntity<?> deleteAsAdmin(@PathVariable String id){
        try {
            User user = service.getUserByAuth0Id(id);
            if (user != null) {
                service.delete(user.getId());
                auth0Service.deleteAuth0User(id);
            }else
                throw new Exception("No existe el usuario");
            return ResponseEntity.status(HttpStatus.OK).body("{\"mensaje\":\"Eliminado Correctamente\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("addresses/{id}")
    public ResponseEntity<?> getAddresses(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getUserAddresses(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("phones/{id}")
    public ResponseEntity<?> getPhones(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getUserPhones(id));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
    @GetMapping("getTop5UsersActual")
    public ResponseEntity<?> getTop5Users(@RequestParam int limit,@RequestParam String orderBy){
        try {
            if (orderBy.equals("orders")){
                 return ResponseEntity.status(HttpStatus.OK).body(service.getTop5UsersOrders(limit));
            }
            if (orderBy.equals("price")){
                return ResponseEntity.status(HttpStatus.OK).body(service.getTop5UsersPrice(limit));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"el order by no es ni orden ni precio. Por favor intente luego\"}");

        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
    @GetMapping("getTopUsersByOrderDateRange")
    public ResponseEntity<?> getTopUsersByOrderDateRange(@RequestParam Date startDate,@RequestParam Date endDate, @RequestParam int limit,@RequestParam String orderBy){
        try {
            if (orderBy.equals("price")){
                return ResponseEntity.status(HttpStatus.OK).body(service.getUsersWithMostPrice(startDate,endDate,limit));
            }
            if (orderBy.equals("orders")) {
                return ResponseEntity.status(HttpStatus.OK).body(service.getUsersWithMostOrders(startDate,endDate,limit));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"el order by no es ni orden ni precio. Por favor intente luego\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("auth0/{id}")
    public ResponseEntity<?> getUserByAuth0Id(@PathVariable String id){
        try {
            User user = service.getUserByAuth0Id(id);
            if (user != null) {
                return ResponseEntity.status(HttpStatus.OK).body(user);
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new User());
            }
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("/Empleoyees")
    public ResponseEntity<?> getAllEmpleoyees(@RequestParam(required = false)String rol, @RequestParam(required = false)String name ){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getEmpleoyees(rol,name));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
    @GetMapping("/Clients")
    public ResponseEntity<?> getAllClients(@RequestParam(required = false)String name){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.getClients(name));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping("/sendReview")
    public ResponseEntity<?> sendReview(@RequestBody Review entity){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mailService.sendReview(entity));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }

    @GetMapping ("/update-password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable String id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body("{\"ticket\":\""+ auth0Service.getPasswordChange(id) +"\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
}
