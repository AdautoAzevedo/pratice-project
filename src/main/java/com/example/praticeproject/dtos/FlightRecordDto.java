package com.example.praticeproject.dtos;

import java.util.Set;

public record FlightRecordDto(Long id, String origin, String destination, int duration, int seats, Set<SimplifiedPassengerDto> passengers) {
}
