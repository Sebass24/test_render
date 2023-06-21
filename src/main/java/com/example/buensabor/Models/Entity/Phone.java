package com.example.buensabor.Models.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="phone")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class Phone extends Base{
    private String number;

    @ManyToOne
    private User user;

}
