package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.FixedEntities.ProductCategory;
import com.example.buensabor.Models.FixedEntities.Role;
import com.example.buensabor.Repositories.ProductCategoryRepository;
import com.example.buensabor.Repositories.RoleRepository;
import com.example.buensabor.Services.ProductCategoryService;
import com.example.buensabor.Services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<Role,Long> implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        super(roleRepository);
        this.roleRepository = roleRepository;
    }

}
