package com.example.praticeproject.controllers;

import java.util.List;
import java.util.Optional;

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

import com.example.praticeproject.dtos.FlightReceivedDto;
import com.example.praticeproject.dtos.FlightRecordDto;
import com.example.praticeproject.models.Flight;
import com.example.praticeproject.services.FlightService;

@Controller
@RequestMapping("/flights")
public class FlightController {
    @Autowired
    private FlightService flightService;

    @GetMapping
    public ResponseEntity<List<FlightRecordDto>> getAllFlights() {
        List<FlightRecordDto> flights = flightService.getAllFlights();
        System.out.println(flights);
        return ResponseEntity.ok(flights);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightRecordDto> getFlightById(@PathVariable Long id) {
        Optional<FlightRecordDto> flight = flightService.getFlightById(id);
        return flight.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Flight> saveFlight(@RequestBody FlightReceivedDto flightReceivedDto) {
        Flight savedFlight = flightService.saveFlight(flightReceivedDto);
        return ResponseEntity.ok().body(savedFlight);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFlight(@PathVariable Long id) {
        flightService.deleteFlightRecord(id);
        return ResponseEntity.status(HttpStatus.OK).body("Flight deleted");
    }
    
    @PostMapping("/{flightId}/passengers/{passengerId}")
    public ResponseEntity<Object> addPassengerToFlight(@PathVariable Long flightId, @PathVariable Long passengerId) {
        return flightService.addPassengerToFlight(flightId, passengerId);
    }
    @GetMapping("/byAirport/{airportId}")
    public ResponseEntity<List<Flight>> getFlightsByAirport(@PathVariable Long airportId) {
        return flightService.getFlightsByAirport(airportId);
    }
}
