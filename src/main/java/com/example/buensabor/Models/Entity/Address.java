package com.example.buensabor.Models.Entity;

import com.example.buensabor.Models.FixedEntities.Location;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="address")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class Address extends Base{
    private String street;
    private String number;

    @OneToOne
    private Location location;

    @ManyToOne
    private User user;
}
