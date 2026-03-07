package com.restaurant_booking.service;

import com.restaurant_booking.dto.ReservationRequestDto;
import com.restaurant_booking.dto.ReservationResponseDto;
import com.restaurant_booking.model.Reservation;
import com.restaurant_booking.repository.ReservationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // ------------------------
    // CREATE RESERVATION
    // ------------------------
    public ReservationResponseDto createReservation(ReservationRequestDto request) {

        if (request.getTableId() == null || request.getStartTime() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "tableId and startTime are required."
            );
        }

        int duration = (request.getDurationHours() == null)
                ? 2
                : request.getDurationHours();

        if (duration <= 0 || duration > 6) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "durationHours must be between 1 and 6."
            );
        }

        LocalDateTime start = request.getStartTime();
        LocalDateTime end = start.plusHours(duration);

        // Overlap check
        var conflicts = reservationRepository.findOverlappingReservations(
                request.getTableId(),
                start,
                end
        );

        if (!conflicts.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Table is already reserved for the selected time."
            );
        }

        // Map DTO -> Entity
        Reservation reservation = new Reservation();
        reservation.setTableId(request.getTableId());
        reservation.setStartTime(start);
        reservation.setEndTime(end);
        reservation.setIncludesDishOfTheDay(request.isIncludesDishOfTheDay());
        reservation.setReservationCode(UUID.randomUUID().toString());

        Reservation saved = reservationRepository.save(reservation);

        return mapToResponse(saved);
    }

    // ------------------------
    // LOOKUP RESERVATION
    // ------------------------
    public ReservationResponseDto getByCode(String code) {

        Reservation reservation = reservationRepository.findByReservationCode(code)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Reservation not found."
                        )
                );

        return mapToResponse(reservation);
    }

    // ------------------------
    // MAPPER METHOD
    // ------------------------
    private ReservationResponseDto mapToResponse(Reservation reservation) {

        ReservationResponseDto response = new ReservationResponseDto();
        response.setReservationCode(reservation.getReservationCode());
        response.setTableId(reservation.getTableId());
        response.setStartTime(reservation.getStartTime());
        response.setEndTime(reservation.getEndTime());
        response.setIncludesDishOfTheDay(reservation.isIncludesDishOfTheDay());

        return response;
    }
}