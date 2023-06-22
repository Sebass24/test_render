package com.example.buensabor.Controllers;

import com.example.buensabor.Models.Entity.Image;
import com.example.buensabor.Services.Impl.ImageServiceImpl;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    @GetMapping("see/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws IOException {
        // Cargar la imagen desde la carpeta de uploads
        Path imagePath = Paths.get(new File("").getAbsolutePath() + "/src/main/resources/static/" + filename);
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists()) {
            MediaType mediaType = getMediaType(filename);
            // Leer los bytes de la imagen en un arreglo
            byte[] imageBytes = Files.readAllBytes(imagePath);

            // Configurar la cabecera de la respuesta para mostrar la imagen en el navegador
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);

            return new ResponseEntity(imageBytes, headers, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MediaType getMediaType(String filename) {
        String extension = FilenameUtils.getExtension(filename);
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            // Agrega más casos para otros formatos de imagen si es necesario
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }



}
