package com.example.buensabor.Models.Entity;

import com.example.buensabor.Models.FixedEntities.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="user")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class User extends Base {

    private String userEmail;
    private String password;

    @OneToOne
    private Role role;

    private String name;
    private String lastName;

    @Column(name = "auth0_id")
    private String auth0Id;

//    @OneToMany(mappedBy = "user")
//    private Set<Address> addresses;
//
//    @OneToMany(mappedBy = "user")
//    private Set<Phone> phones;

}
