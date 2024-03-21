package com.example.praticeproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.praticeproject.models.Airport;
import com.example.praticeproject.repositories.AirportRepository;
import com.example.praticeproject.services.AirportService;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {
    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private AirportService airportService;

    @Test
    public void testRegisterAirport() {
        Airport airport = new Airport();
        when(airportRepository.save(any(Airport.class))).thenReturn(airport);
        
        Airport registeredAirport = airportService.registerAirport(airport).getBody();
        
        assertNotNull(registeredAirport);
        assertEquals(airport, registeredAirport);
    }

    @Test
    public void testGetAllAirports() {
        List<Airport> airports = new ArrayList<>();

        when(airportRepository.findAll()).thenReturn(airports);

        ResponseEntity<List<Airport>> receivedList = airportService.getAllAirports();
        assertNotNull(receivedList);
        assertEquals(airports, receivedList.getBody()
        );
    }

    @Test
    public void testGetAirportById() {
        Airport airport = new Airport();
        airport.setId(1L);

        when(airportRepository.findById(1L)).thenReturn(Optional.of(airport));

        ResponseEntity<Airport> receivedAirport = airportService.getAirportById(1L);
        assertNotNull(receivedAirport);
        assertEquals(airport, receivedAirport.getBody());
    }

    @Test
    public void testDeleteAirport() {
        Long id = 1L;
        doNothing().when(airportRepository).deleteById(id);
        airportService.deleteAirport(id);
        verify(airportRepository, times(1)).deleteById(id);
    }
}
