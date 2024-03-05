package com.example.praticeproject.dtos;

import java.util.Set;

public record PassengerRecordDto(Long id, String name, Set<SimplifiedFlightDto> flightDtos) {
}
