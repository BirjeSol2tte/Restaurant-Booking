package com.restaurant_booking.controller;

import com.restaurant_booking.model.Reservation;
import com.restaurant_booking.repository.ReservationRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {

        // Check overlapping reservations for this table
        var conflicts = reservationRepository.findOverlappingReservations(
            reservation.getTableId(),
            reservation.getStartTime(),
            reservation.getEndTime()
        );

        if (!conflicts.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, 
                "Table is already reserved for the selected time."
            );
        }

        //Generate simple reservation code
        reservation.setReservationCode(UUID.randomUUID().toString());

        return reservationRepository.save(reservation);
    }
}


