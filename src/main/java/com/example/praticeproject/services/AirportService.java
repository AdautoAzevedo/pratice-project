package com.example.praticeproject.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.praticeproject.dtos.AirportFlightsDto;
import com.example.praticeproject.dtos.SimplifiedFlightDto;
import com.example.praticeproject.models.Airport;
import com.example.praticeproject.models.Flight;
import com.example.praticeproject.repositories.AirportRepository;
import com.example.praticeproject.repositories.FlightRepository;

@Service
public class AirportService {
    
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository, FlightRepository flightRepository) {
        this.airportRepository = airportRepository;
        this.flightRepository = flightRepository;
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

    public ResponseEntity<AirportFlightsDto> getFlightsByAirport(Long airportId) {
        Airport airport = airportRepository.findById(airportId).get();
        List<Flight> departures = flightRepository.findByOrigin(airport);
        List<Flight> arrivals = flightRepository.findByDestination(airport);

        List<SimplifiedFlightDto> arrivalsDto = mapFlightsToDto(arrivals);
        List<SimplifiedFlightDto> departuresDto = mapFlightsToDto(departures);

        AirportFlightsDto flights = new AirportFlightsDto(airport.getName(), arrivalsDto, departuresDto);
        
        return ResponseEntity.ok().body(flights);
    }

    private List<SimplifiedFlightDto> mapFlightsToDto(List<Flight> flights) {
        return flights.stream()
                .map(this::simplifyFlight)
                .collect(Collectors.toList());
    }

    private SimplifiedFlightDto simplifyFlight(Flight flight) {
        return new SimplifiedFlightDto(flight.getId(), flight.getOrigin().getName(), flight.getDestination().getName());
    }
}
