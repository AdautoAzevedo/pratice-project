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

import com.example.praticeproject.dtos.FlightRecordDto;
import com.example.praticeproject.models.Flight;
import com.example.praticeproject.models.Passenger;
import com.example.praticeproject.services.FlightService;
import com.example.praticeproject.services.PassengerService;

@Controller
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @Autowired
    private PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<FlightRecordDto>> getAllFlights() {
        List<FlightRecordDto> flights = flightService.getAllFlights();
        System.out.println(flights);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightRecordDto> getFlightById(@PathVariable Long id) {
        Optional<FlightRecordDto> flight = flightService.getFlightById(id);
        System.out.println(flight);
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
    
    @PostMapping("/{flightId}/passengers/{passengerId}")
    public ResponseEntity<Object> addPassengerToFlight(@PathVariable Long flightId, @PathVariable Long passengerId) {
        Optional<Flight> optionalFlight = flightService.getFlightObjectById(flightId);
        Optional<Passenger> optionalPassenger = passengerService.getPassengerById(passengerId);

        if (optionalFlight != null && optionalPassenger != null) {
            Flight flight = optionalFlight.get();
            Passenger passenger = optionalPassenger.get();
            flight.getPassengers().add(passenger);
            flightService.saveFlight(flight);
        }

        return ResponseEntity.ok().body("Passenger added");

    }
}
