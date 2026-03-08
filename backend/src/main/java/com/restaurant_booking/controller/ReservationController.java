package com.restaurant_booking.controller;

import com.restaurant_booking.dto.ReservationRequestDto;
import com.restaurant_booking.dto.ReservationResponseDto;
import com.restaurant_booking.dto.UpdateDishRequestDto;
import com.restaurant_booking.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationResponseDto createReservation(@RequestBody ReservationRequestDto request) {
        return reservationService.createReservation(request);
    }

    @GetMapping("/{code}")
    public ReservationResponseDto getReservationByCode(@PathVariable String code) {
        return reservationService.getByCode(code);
    }

    @DeleteMapping("/{code}")
    public void deleteReservation(@PathVariable String code) {
        reservationService.deleteByCode(code);
    }

    @PutMapping("/{code}/dish")
    public ReservationResponseDto updateDishChoice(
            @PathVariable String code,
            @RequestBody UpdateDishRequestDto request
    ) {
        return reservationService.updateDishChoice(code, request);
    }
}