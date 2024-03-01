package com.example.praticeproject.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.praticeproject.models.Flight;
import com.example.praticeproject.models.Passenger;
import com.example.praticeproject.services.FlightService;

@Controller
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        return flight.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Flight> saveFlight(@RequestBody Flight flight) {
        Flight savedFlight = flightService.saveFlight(flight);
        return ResponseEntity.ok().body(savedFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlightRecord(id);
        return ResponseEntity.status(HttpStatus.OK).body("Flight deleted");
    }
    
    @PostMapping("/{id}")
    public ResponseEntity<Flight> addPassengerToFlight(@PathVariable Long id, @RequestBody Passenger passenger) {
        Optional<Flight> optionalFlight = flightService.getFlightById(id);
        if (optionalFlight.isPresent()) {
            Flight flight = optionalFlight.get();

            Set<Passenger> passengers = flight.getPassengers();
            passengers.add(passenger);
            flight.setPassengers(passengers);
            Flight updatedFlight = flightService.saveFlight(flight);
            return ResponseEntity.ok(updatedFlight);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
