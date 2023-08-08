package com.carrental.carrental.services;

import com.carrental.carrental.entities.Location;
import com.carrental.carrental.repositories.LocationRepositories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private final LocationRepositories locationRepositories;

    public LocationService(LocationRepositories locationRepositories) {
        this.locationRepositories = locationRepositories;
    }

    public List<Location> findAllLocations() {
        return locationRepositories.findAll();
    }

    public Location findLocationByName(String location) {
        return locationRepositories.findByLocationName(location);
    }
}
