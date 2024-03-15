package com.example.praticeproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.praticeproject.dtos.AirportFlightsDto;
import com.example.praticeproject.models.Airport;
import com.example.praticeproject.services.AirportService;

@Controller
@RequestMapping("/airports")
public class AirportController {
    @Autowired
    private AirportService airportService;

    @GetMapping
    public ResponseEntity<List<Airport>> getAllAirports() {
        return airportService.getAllAirports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Airport> getAirportById(@PathVariable Long id) {
        return airportService.getAirportById(id);
    }

    @PostMapping
    public ResponseEntity<Airport> saveAirport(@RequestBody Airport airport) {
        return airportService.registerAirport(airport);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAirport(@PathVariable Long id) {
        return airportService.deleteAirport(id);
    }

    @GetMapping("/flights/{airportId}")
    public ResponseEntity<AirportFlightsDto> getFlightByAirport(@PathVariable Long airportId) {
        return airportService.getFlightsByAirport(airportId);
    }
}
