package com.example.praticeproject.dtos;

import java.util.Set;

public record FlightRecordDto(Long id, String origin, String destiny, int duration, int seats, Set<String> passengerName) {
}
