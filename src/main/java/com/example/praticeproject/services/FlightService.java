package com.example.praticeproject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.praticeproject.dtos.FlightRecordDto;
import com.example.praticeproject.models.Flight;
import com.example.praticeproject.models.Passenger;
import com.example.praticeproject.repositories.FlightRepository;

@Service
@Transactional
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

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

    private FlightRecordDto convertToDto(Flight flight) {
        Set<String> passengerNames = flight.getPassengers().stream()
                                                .map(Passenger::getName)
                                                .collect(Collectors.toSet());
        FlightRecordDto dto = new FlightRecordDto(flight.getId(), flight.getOrigin(), flight.getDestiny(), flight.getDuration(), flight.getSeats(), passengerNames);
        return dto;
    }
}
