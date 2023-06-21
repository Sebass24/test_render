package com.example.buensabor.Controllers;

import com.example.buensabor.Models.Entity.Image;
import com.example.buensabor.Services.Impl.ImageServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "api/image")
public class ImageController extends BaseControllerImpl<Image, ImageServiceImpl>{

    public ImageController(ImageServiceImpl service) {
        super(service);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file){

        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.save(file));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. Al cargar imagen\"}");
        }


    }

    @GetMapping("/file/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) throws IOException {
        // Obtén el archivo MultipartFile desde alguna fuente
        MultipartFile file = service.getImageById(id);

        // Crea un InputStreamResource a partir del archivo
        InputStreamResource resource = new InputStreamResource(file.getInputStream());

        // Configura las cabeceras de la respuesta
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getOriginalFilename());
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        // Devuelve la respuesta con el archivo adjunto
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
