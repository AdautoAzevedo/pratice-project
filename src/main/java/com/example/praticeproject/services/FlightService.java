package com.example.praticeproject.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.praticeproject.dtos.FlightRecordDto;
import com.example.praticeproject.dtos.SimplifiedPassengerDto;
import com.example.praticeproject.models.Flight;
import com.example.praticeproject.models.Passenger;
import com.example.praticeproject.repositories.FlightRepository;

@Service
@Transactional
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private PassengerService passengerService;

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public List<FlightRecordDto> getAllFlights() {
        return flightRepository.findAll().stream()
                .map(this::convertToDto)    
                .collect(Collectors.toList());
    }

    
    public Optional<FlightRecordDto> getFlightById(Long id) {
        return flightRepository.findById(id).map(this::convertToDto);
    }
    
    public Optional<Flight> getFlightObjectById(Long id) {
        return flightRepository.findById(id);
    }

    public void deleteFlightRecord(Long id) {
        flightRepository.deleteById(id);
    }

    public ResponseEntity<Object> addPassengerToFlight(Long flightId, Long passengerId) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        Optional<Passenger> optionalPassenger = passengerService.getPassengerObjectById(passengerId);

        if (optionalFlight.isPresent() && optionalPassenger.isPresent()) {
            Flight flight = optionalFlight.get();
            Passenger passenger = optionalPassenger.get();
            
            if (flight.getSeats() > flight.getPassengers().size()) {
                flight.getPassengers().add(passenger);
                flight.setSeats(flight.getSeats() - 1);
                flightRepository.save(flight);
                return ResponseEntity.ok().body("Passenger added to flight");
            } else {
                return ResponseEntity.badRequest().body("Flight is full. Cannot add passenger");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private FlightRecordDto convertToDto(Flight flight) {
        Set<SimplifiedPassengerDto> passengerRecordDtos = flight.getPassengers().stream()
                .map(passenger -> {
                    SimplifiedPassengerDto passengerDto = new SimplifiedPassengerDto(passenger.getId(), passenger.getName());
                    return passengerDto;
                })
                .collect(Collectors.toSet());

        FlightRecordDto dto = new FlightRecordDto(flight.getId(), flight.getOrigin(), flight.getDestiny(), flight.getDuration(), flight.getSeats(), passengerRecordDtos);
        return dto;
    }
}
