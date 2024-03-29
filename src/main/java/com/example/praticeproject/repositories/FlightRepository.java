package com.example.praticeproject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.praticeproject.models.Airport;
import com.example.praticeproject.models.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{
    List<Flight> findByOrigin(Airport origin);
    List<Flight> findByDestination(Airport destination);
}
