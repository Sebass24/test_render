package com.example.buensabor.Services.Impl;

import com.example.buensabor.Exceptions.ServiceException;
import com.example.buensabor.Models.Entity.Base;
import com.example.buensabor.Repositories.BaseRepository;
import com.example.buensabor.Services.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseServiceImpl<T extends Base, ID extends Serializable> implements BaseService<T, ID> {

    protected BaseRepository<T,ID> baseRepository;

    public BaseServiceImpl(BaseRepository<T, ID> baseRepository)
    {
        this.baseRepository = baseRepository;
    }

    @Override
    @Transactional
    public List<T> findAll() throws ServiceException {
        try {
            return baseRepository.findAll();
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<T> findAllActive() throws ServiceException {
        try {
            return baseRepository.findAll().stream().filter(entity -> !entity.isDeleted()).collect(Collectors.toList());
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public T findById(ID id) throws ServiceException {
        try {
            Optional<T> entityOptional = baseRepository.findById(id);
            return entityOptional.get();
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public T save(T entity) throws ServiceException {
        try {
            entity = baseRepository.save(entity);
            return entity;
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public T update(T entity) throws ServiceException {
        try {
            if (entity.getId() == null) {
                throw new ServiceException("La entidad a modificar debe contener un Id.");
            }
            return baseRepository.save(entity);
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws ServiceException {
        try {
            Optional<T> entityOptional = baseRepository.findById(id);
            if (entityOptional.isPresent()) {
                entityOptional
                        .get()
                        .setDeleted(true);
                baseRepository.save(entityOptional.get());
                return true;
            }else {
                throw new Exception("No existe la entidad");
            }
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Page<T> findAll(Pageable pageable) throws ServiceException {
        try {
            Page<T> entities = baseRepository.findAll(pageable);
            return entities;
        }catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
