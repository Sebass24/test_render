package com.example.buensabor.Controllers.FixedEntitiesControllers;

import com.example.buensabor.Controllers.BaseControllerImpl;
import com.example.buensabor.Models.FixedEntities.Role;
import com.example.buensabor.Services.Impl.Auth0Service;
import com.example.buensabor.Services.Impl.RoleServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/role")
public class RoleController extends BaseControllerImpl<Role, RoleServiceImpl> {

    private Auth0Service auth0Service;
    public RoleController(RoleServiceImpl service, Auth0Service auth0Service) {
        super(service);
        this.auth0Service = auth0Service;
    }

    @GetMapping("change/{userId}/{roleId}")
    public ResponseEntity<?> assingRole(@PathVariable String userId, @PathVariable String roleId){
        try {
            auth0Service.assignRoleToUser(userId,roleId);
            return ResponseEntity.status(HttpStatus.OK).body("{\"mesage\":\"Rol asignado\"}");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Por favor intente luego\"}");
        }
    }
}
