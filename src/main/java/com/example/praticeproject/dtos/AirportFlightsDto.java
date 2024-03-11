package com.example.praticeproject.dtos;

import java.util.List;

public record AirportFlightsDto(String name, List<SimplifiedFlightDto> arrivals, List<SimplifiedFlightDto> departures) {
}
