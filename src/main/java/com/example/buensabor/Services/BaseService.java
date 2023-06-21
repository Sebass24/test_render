package com.example.buensabor.Services;

import com.example.buensabor.Models.Entity.Base;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

//Servicios Genericos
public interface BaseService<T extends Base, ID extends Serializable> {
    List<T> findAll() throws Exception;
    List<T> findAllActive() throws Exception;
    Page<T> findAll(Pageable pageable) throws Exception;
    T findById(ID id) throws Exception;
    T save(T entity) throws Exception;
    T update(T entity) throws Exception;
    boolean delete(ID id) throws Exception;
}
