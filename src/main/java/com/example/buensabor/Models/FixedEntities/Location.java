package com.example.buensabor.Models.FixedEntities;

import com.example.buensabor.Models.Entity.Base;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="location")
@Data //Getters and Setters
@NoArgsConstructor
@AllArgsConstructor
public class Location extends Base {
    private String name;

//    Capital("Capital"),
//    Guaymallen("Guaymallén"),
//    LasHeras("Las Heras"),
//    Lavalle("Lavalle"),
//    Junin("Junín"),
//    LaPaz("La Paz"),
//    Maipu("Maipú"),
//    Rivadavia("Rivadavia"),
//    SantaRosa("Santa Rosa"),
//    SanMartín("San Martín"),
//    GodoyCruz("Godoy Cruz"),
//    LujánDeCuyo("Lujan de Cuyo"),
//    SanCarlos("San Carlos"),
//    Tunuyán("Tunuyán"),
//    Tupungato("Tupungato"),
//    GeneralAlvear("General Alvear"),
//    Malargüe("Malargüe"),
//    SanRafael("San Rafael")
//    ;

}
