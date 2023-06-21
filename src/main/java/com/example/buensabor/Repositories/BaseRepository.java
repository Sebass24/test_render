package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

//Repository Generico
@NoRepositoryBean
public interface BaseRepository<T extends Base, ID extends Serializable> extends JpaRepository<T,ID> {

//    Ejemplo metodo con query
//    @Query("select i from Instrumento i where i.nombre like %:filtro% or i.marca like %:filtro% or i.modelo like %:filtro%")
//    List<Instrumento> search(@Param("filtro") String filtro);
}

