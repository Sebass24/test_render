package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends BaseRepository<Address,Long> {

    @Query("select a from Address a where a.user.id = :id and a.deleted = false ")
    List<Address> getAddressesByUser(@Param("id") Long id);

}
