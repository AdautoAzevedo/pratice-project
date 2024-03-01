package com.example.praticeproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.praticeproject.models.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>{

}
