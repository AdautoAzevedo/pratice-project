package com.example.praticeproject.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<Passenger> getAllPassengers() {
        return passengerRepository.findAll();
    }

    public Optional<Passenger> getPassengerById(Long id) {
        return passengerRepository.findById(id);
    }

    public void deletePassengerRecord(Long id) {
        passengerRepository.deleteById(id);
    }
}
