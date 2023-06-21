package com.example.buensabor.Repositories;

import com.example.buensabor.Models.Entity.Bill;
import com.example.buensabor.Models.Entity.Image;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends BaseRepository<Image,Long> {
    
}
