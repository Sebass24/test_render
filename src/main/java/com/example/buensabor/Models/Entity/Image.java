package com.example.buensabor.Models.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name="image")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class Image extends Base{
    private String name;
    private String path;
    @Transient
    private MultipartFile imagen;
}
