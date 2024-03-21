package com.example.praticeproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
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

import com.example.praticeproject.dtos.PassengerRecordDto;
import com.example.praticeproject.models.Passenger;
import com.example.praticeproject.repositories.PassengerRepository;
import com.example.praticeproject.services.PassengerService;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceTest {
    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    public void testSavePassenger() {
        Passenger passenger = new Passenger(1l, "Passenger A", new HashSet<>());
        when(passengerRepository.save(passenger)).thenReturn(passenger);

        Passenger passengerReceived = passengerService.savePassenger(passenger);

        assertEquals(passenger, passengerReceived);
        verify(passengerRepository, times(1)).save(passenger);   
    }

    @Test
    public void testGetAllPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger());
        passengers.add(new Passenger());

        when(passengerRepository.findAll()).thenReturn(passengers);

        List<PassengerRecordDto> passengerDto = passengerService.getAllPassengers();

        assertEquals(passengers.size(), passengerDto.size());
        verify(passengerRepository, times(1)).findAll();
    }

    @Test
    public void testGetPassengerObjectById() {
        Passenger passenger = new Passenger(1L, "Passenger A", new HashSet<>());
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        Optional<Passenger> passengerFound = passengerService.getPassengerObjectById(1L);
        assertTrue(passengerFound.isPresent());
        assertEquals(passenger, passengerFound.get());
    }

    @Test
    public void testGetPassengerById() {
        Passenger passenger = new Passenger();
        passenger.setId(1L);
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        Optional<PassengerRecordDto> receivedPassenger = passengerService.getPassengerById(1L);

        assertTrue(receivedPassenger.isPresent());
        assertEquals(passenger.getId(), receivedPassenger.get().id());
    }

    @Test
    public void testDeletePassengerRecord() {
        Long id = 1L;
        doNothing().when(passengerRepository).deleteById(id);
        passengerService.deletePassengerRecord(id);
        verify(passengerRepository, times(1)).deleteById(id);
    }
}
