package com.example.praticeproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.praticeproject.models.Airport;
import com.example.praticeproject.repositories.AirportRepository;

@Service
public class AirportService {
    
    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public ResponseEntity<Airport> registerAirport(Airport airport) {
        Airport savedAirport = airportRepository.save(airport);
        return ResponseEntity.ok().body(savedAirport);
    }

    public ResponseEntity<List<Airport>> getAllAirports() {
        List<Airport> airports = airportRepository.findAll();
        return ResponseEntity.ok().body(airports);
    }

    public ResponseEntity<Airport> getAirportById(Long id) {
        Optional<Airport> airport = airportRepository.findById(id);
        return airport.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<Object> deleteAirport(Long id) {
        try {
            airportRepository.deleteById(id);
            return ResponseEntity.ok().body("Airport deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting airport");
        }
    }
}
