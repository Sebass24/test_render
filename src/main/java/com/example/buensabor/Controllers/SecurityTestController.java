package com.example.buensabor.Controllers;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;


@RestController
@RequestMapping(path = "api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class SecurityTestController {

    @GetMapping(value = "/public")
    public ResponseEntity<?> publicEndpoint() {
        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"All good. You DO NOT need to be authenticated to call this endpoint.\"}");
    }

    @GetMapping(value = "/private")
    public ResponseEntity<?> privateEndpoint(@RequestHeader ("Authorization") String jwt) {
        String aux = jwt.substring(7);

        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(aux).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"All good. You can see this because you are Authenticated.\"}");
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> adminOnlyEndpoint() {
        return ResponseEntity.status(HttpStatus.OK).body("{ \"message\": \"All good. You can see this because you are an Admin.\"}");
    }

}
