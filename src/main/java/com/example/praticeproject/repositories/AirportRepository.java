package com.example.praticeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.praticeproject.models.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long> {
    
}
