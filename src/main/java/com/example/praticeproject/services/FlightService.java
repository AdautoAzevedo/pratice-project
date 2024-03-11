package com.example.praticeproject.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.praticeproject.dtos.AirportFlightsDto;
import com.example.praticeproject.dtos.FlightReceivedDto;
import com.example.praticeproject.dtos.FlightRecordDto;
import com.example.praticeproject.dtos.SimplifiedFlightDto;
import com.example.praticeproject.dtos.SimplifiedPassengerDto;
import com.example.praticeproject.models.Airport;
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

    @Autowired
    private AirportService airportService;

    public Flight saveFlight(FlightReceivedDto flightReceivedDto) {
        Airport origin = airportService.getAirportById(flightReceivedDto.origin()).getBody();
        Airport destination = airportService.getAirportById(flightReceivedDto.destination()).getBody();
        Flight flight = new Flight();
        flight.setSeats(flightReceivedDto.seats());
        flight.setDuration(flightReceivedDto.duration());
        flight.setOrigin(origin);
        flight.setDestination(destination);
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

    public ResponseEntity<AirportFlightsDto> getFlightsByAirport(Long airportId) {
        Airport airport = airportService.getAirportById(airportId).getBody();
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

    private FlightRecordDto convertToDto(Flight flight) {
        Set<SimplifiedPassengerDto> passengerRecordDtos = flight.getPassengers().stream()
                .map(passenger -> {
                    SimplifiedPassengerDto passengerDto = new SimplifiedPassengerDto(passenger.getId(), passenger.getName());
                    return passengerDto;
                })
                .collect(Collectors.toSet());

        FlightRecordDto dto = new FlightRecordDto(flight.getId(), flight.getOrigin().getName(), flight.getDestination().getName(), flight.getDuration(), flight.getSeats(), passengerRecordDtos);
        return dto;
    }
}
