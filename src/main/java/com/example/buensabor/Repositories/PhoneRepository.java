package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.Phone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends BaseRepository<Phone,Long> {

    @Query("select p from Phone p where p.user.id = :id and p.deleted = false ")
    List<Phone> getPhonesByUser(@Param("id") Long id);

}
