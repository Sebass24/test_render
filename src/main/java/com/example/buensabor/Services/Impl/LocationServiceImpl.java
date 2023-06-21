package com.example.buensabor.Services.Impl;

import com.example.buensabor.Models.FixedEntities.Location;
import com.example.buensabor.Repositories.LocationRepository;
import com.example.buensabor.Services.LocationService;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends BaseServiceImpl<Location,Long> implements LocationService {

    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        super(locationRepository);
        this.locationRepository = locationRepository;
    }

}
