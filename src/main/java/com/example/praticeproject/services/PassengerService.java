package com.example.praticeproject.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.praticeproject.dtos.PassengerRecordDto;
import com.example.praticeproject.dtos.SimplifiedFlightDto;
import com.example.praticeproject.models.Passenger;
import com.example.praticeproject.repositories.PassengerRepository;

@Service
@Transactional
public class PassengerService {
    @Autowired
    private PassengerRepository passengerRepository;

    public Passenger savePassenger(Passenger passenger) {
        return passengerRepository.save(passenger);
    }

    public List<PassengerRecordDto> getAllPassengers() {
        return passengerRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<PassengerRecordDto> getPassengerById(Long id) {
        return passengerRepository.findById(id).map(this::convertToDto);
    }

    public Optional<Passenger> getPassengerObjectById(Long id) {
        return passengerRepository.findById(id);
    }

    public void deletePassengerRecord(Long id) {
        passengerRepository.deleteById(id);
    }

    private PassengerRecordDto convertToDto(Passenger passenger) {
        Set<SimplifiedFlightDto> simplifiedFlightDtos = passenger.getFlights().stream()
                .map(flight -> new SimplifiedFlightDto(flight.getId(), flight.getOrigin(), flight.getDestiny()))
                .collect(Collectors.toSet());

        return new PassengerRecordDto(passenger.getId(), passenger.getName(), simplifiedFlightDtos);
    }

}
