package com.example.praticeproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.praticeproject.dtos.FlightReceivedDto;
import com.example.praticeproject.dtos.FlightRecordDto;
import com.example.praticeproject.models.Airport;
import com.example.praticeproject.models.Flight;
import com.example.praticeproject.models.Passenger;
import com.example.praticeproject.repositories.FlightRepository;
import com.example.praticeproject.services.AirportService;
import com.example.praticeproject.services.FlightService;
import com.example.praticeproject.services.PassengerService;

@ExtendWith(MockitoExtension.class)
public class FlightServiceTest {
    @Mock
    private FlightRepository flightRepository;

    @Mock
    private PassengerService passengerService;

    @Mock
    private AirportService airportService;

    @InjectMocks
    private FlightService flightService;

    @Test
    public void testSaveFlight() {

        FlightReceivedDto flightReceivedDto = new FlightReceivedDto(1L, 2L, 30, 120);
        Airport origin = new Airport();
        origin.setId(1L);
        Airport destination = new Airport();
        destination.setId(2L);

        when(airportService.getAirportById(1L)).thenReturn(ResponseEntity.ok().body(origin));
        when(airportService.getAirportById(2L)).thenReturn(ResponseEntity.ok().body(destination));

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setOrigin(origin);
        flight.setDestination(destination);
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        
        Flight savedFlight = flightService.saveFlight(flightReceivedDto);
        
        assertNotNull(savedFlight);
        assertEquals(1L, savedFlight.getId());
        assertEquals(origin, savedFlight.getOrigin());
        assertEquals(destination, savedFlight.getDestination());
        verify(flightRepository, times(1)).save(any(Flight.class));  
    }

    @Test
    public void testGetAllFlights() {
        List<Flight> flights = new ArrayList<>();
        flights.add(new Flight(1L, new Airport(), new Airport(), 40, 100, new HashSet<>()));
        flights.add(new Flight(2L, new Airport(), new Airport(), 40, 100, new HashSet<>()));

        when(flightRepository.findAll()).thenReturn(flights);

        List<FlightRecordDto> flightsList = flightService.getAllFlights();

        assertEquals(2, flightsList.size());

    }

    @Test
    public void testGetFlightById() {
        Flight flight = new Flight(1L, new Airport(), new Airport(), 40, 100, new HashSet<>());

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Optional<FlightRecordDto> flightFound = flightService.getFlightById(1L);

        assertTrue(flightFound.isPresent());
        assertEquals(flight.getId(), flightFound.get().id());
    }

    @Test
    public void testGetFlightObjectById() {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setOrigin(new Airport());
        flight.setDestination(new Airport());

        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Optional<Flight> flightFound = flightService.getFlightObjectById(1L);

        assertTrue(flightFound.isPresent());
        assertEquals(flight, flightFound.get());
    }

    @Test
    public void testAddPassengerToFlight_Success() {
        Flight flight = new Flight();
        flight.setSeats(2);
        Passenger passenger = new Passenger();
        passenger.setId(1L);

        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        when(passengerService.getPassengerObjectById(1L)).thenReturn(Optional.of(passenger));
        
        ResponseEntity<Object> responseEntity = flightService.addPassengerToFlight(1L,1L);

        assertEquals("Passenger added to flight", responseEntity.getBody());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    public void testAddPassengerToFlight_Full() {
        Flight flight = new Flight();
        flight.setSeats(0);
        Passenger passenger = new Passenger();
        passenger.setId(1L);

        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        when(passengerService.getPassengerObjectById(1L)).thenReturn(Optional.of(passenger));
        
        ResponseEntity<Object> responseEntity = flightService.addPassengerToFlight(1L,1L);

        assertEquals("Flight is full. Cannot add passenger", responseEntity.getBody());
        verify(flightRepository, times(0)).save(any(Flight.class));
    }
}
