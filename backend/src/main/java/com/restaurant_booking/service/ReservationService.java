package com.restaurant_booking.service;

import com.restaurant_booking.dto.ReservationRequestDto;
import com.restaurant_booking.dto.ReservationResponseDto;
import com.restaurant_booking.dto.UpdateDishRequestDto;
import com.restaurant_booking.model.Reservation;
import com.restaurant_booking.model.RestaurantTable;
import com.restaurant_booking.repository.ReservationRepository;
import com.restaurant_booking.repository.RestaurantTableRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository tableRepository;
    private final MenuService menuService;

    public ReservationService(ReservationRepository reservationRepository,
                              RestaurantTableRepository tableRepository,
                              MenuService menuService) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
        this.menuService = menuService;
    }

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

        Reservation reservation = new Reservation();
        reservation.setTableId(request.getTableId());
        reservation.setStartTime(start);
        reservation.setEndTime(end);
        reservation.setIncludesDishOfTheDay(request.isIncludesDishOfTheDay());
        reservation.setReservationCode(UUID.randomUUID().toString());

        if (request.isIncludesDishOfTheDay()) {
            LocalDate reservationDate = start.toLocalDate();
            String dishName = menuService.getDishOfTheDay(reservationDate).getName();
            reservation.setDishName(dishName);
        } else {
            reservation.setDishName(null);
        }

        Reservation saved = reservationRepository.save(reservation);
        return mapToResponse(saved);
    }

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

    public void deleteByCode(String code) {
        Reservation reservation = reservationRepository.findByReservationCode(code)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Reservation not found."
                        )
                );

        reservationRepository.delete(reservation);
    }

    public ReservationResponseDto updateDishChoice(String code, UpdateDishRequestDto request) {
        Reservation reservation = reservationRepository.findByReservationCode(code)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Reservation not found."
                        )
                );

        reservation.setIncludesDishOfTheDay(request.isIncludesDishOfTheDay());

        if (request.isIncludesDishOfTheDay()) {
            LocalDate reservationDate = reservation.getStartTime().toLocalDate();
            String dishName = menuService.getDishOfTheDay(reservationDate).getName();
            reservation.setDishName(dishName);
        } else {
            reservation.setDishName(null);
        }

        Reservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    private ReservationResponseDto mapToResponse(Reservation reservation) {
        RestaurantTable table = tableRepository.findById(reservation.getTableId())
                .orElse(null);

        ReservationResponseDto response = new ReservationResponseDto();
        response.setReservationCode(reservation.getReservationCode());
        response.setTableId(reservation.getTableId());
        response.setStartTime(reservation.getStartTime());
        response.setEndTime(reservation.getEndTime());
        response.setIncludesDishOfTheDay(reservation.isIncludesDishOfTheDay());
        response.setDishName(reservation.getDishName());

        if (table != null) {
            response.setTableLabel(table.getTableLabel());
            response.setZone(table.getZone().name());
        }

        return response;
    }
}