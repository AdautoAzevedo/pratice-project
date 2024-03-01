package com.example.praticeproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.praticeproject.models.Flight;
import com.example.praticeproject.repositories.FlightRepository;

@Service
@Transactional
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;

    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    
    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    public void deleteFlightRecord(Long id) {
        flightRepository.deleteById(id);
    }
}
