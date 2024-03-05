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

import com.example.praticeproject.dtos.PassengerRecordDto;
import com.example.praticeproject.models.Passenger;
import com.example.praticeproject.services.PassengerService;

@Controller
@RequestMapping("/passengers")
public class PassengerController {
    @Autowired
    private PassengerService passengerService;

    @GetMapping
    public ResponseEntity<List<PassengerRecordDto>> getAllPassengers() {
        List<PassengerRecordDto> passengers = passengerService.getAllPassengers();
        return ResponseEntity.ok(passengers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerRecordDto> getPassengerById(@PathVariable Long id) {
        Optional<PassengerRecordDto> passenger = passengerService.getPassengerById(id);
        return passenger.map(value -> ResponseEntity.ok().body(value))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Passenger> savePassenger(@RequestBody Passenger passenger) {
        Passenger createdPassenger = passengerService.savePassenger(passenger);
        return ResponseEntity.ok().body(createdPassenger);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassengerRecord(id);
        return ResponseEntity.status(HttpStatus.OK).body("Record deleted");
    }

}
