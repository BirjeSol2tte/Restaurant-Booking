package com.restaurant_booking.service;

import com.restaurant_booking.dto.RecommendationRequestDto;
import com.restaurant_booking.dto.RecommendationResponseDto;
import com.restaurant_booking.model.RestaurantTable;
import com.restaurant_booking.model.Zone;
import com.restaurant_booking.repository.ReservationRepository;
import com.restaurant_booking.repository.RestaurantTableRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class RecommendationService {

    private final RestaurantTableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public RecommendationService(RestaurantTableRepository tableRepository,
                                 ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    public RecommendationResponseDto recommend(RecommendationRequestDto request) {

        if (request.getStartTime() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startTime is required.");
        }
        if (request.getGroupSize() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "groupSize must be >= 1.");
        }

        int duration = (request.getDurationHours() == null) ? 2 : request.getDurationHours();
        if (duration <= 0 || duration > 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "durationHours must be between 1 and 6.");
        }

        LocalDateTime start = request.getStartTime();
        LocalDateTime end = start.plusHours(duration);

        List<RestaurantTable> candidates = tableRepository.findAll().stream()
                .filter(t -> t.getCapacity() >= request.getGroupSize())
                .filter(t -> request.getZone() == null || request.getZone().isBlank()
                        || t.getZone().name().equalsIgnoreCase(request.getZone()))
                .filter(t -> reservationRepository.findOverlappingReservations(t.getId(), start, end).isEmpty())
                .toList();

        if (candidates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No available tables for selected time.");
        }

        RestaurantTable best = candidates.stream()
                .max(Comparator.comparingInt(t -> recommendationScore(t, request)))
                .orElseThrow();

        RecommendationResponseDto response = new RecommendationResponseDto();
        response.setTableId(best.getId());
        response.setTableLabel(best.getTableLabel());
        response.setCapacity(best.getCapacity());
        response.setZone(best.getZone().name());
        response.setStartTime(start);
        response.setEndTime(end);

        return response;
    }

    private int recommendationScore(RestaurantTable table, RecommendationRequestDto request) {
        int score = 0;
        int groupSize = request.getGroupSize();

        // Better capacity fit = higher score
        int wastedSeats = table.getCapacity() - groupSize;
        score += Math.max(0, 30 - (wastedSeats * 4));

        // Zone preference by group size
        if (groupSize <= 2) {
            if (table.getZone() == Zone.QUIET_AREA) score += 20;
            if (table.getZone() == Zone.MAIN_HALL) score += 10;
            if (table.getZone() == Zone.PARTY_ROOM) score -= 25;
        } else if (groupSize <= 4) {
            if (table.getZone() == Zone.MAIN_HALL) score += 18;
            if (table.getZone() == Zone.QUIET_AREA) score += 12;
            if (table.getZone() == Zone.PARTY_ROOM) score -= 20;
        } else if (groupSize <= 8) {
            if (table.getZone() == Zone.MAIN_HALL) score += 12;
            if (table.getZone() == Zone.PRIVATE_ROOM) score += 18;
            if (table.getZone() == Zone.QUIET_AREA) score -= 15;
        } else {
            if (table.getZone() == Zone.PARTY_ROOM) score += 30;
            if (table.getZone() == Zone.PRIVATE_ROOM) score += 12;
            if (table.getZone() == Zone.QUIET_AREA) score -= 30;
        }

        // Explicit quiet-area preference
        if (Boolean.TRUE.equals(request.getQuietArea()) && table.getZone() == Zone.QUIET_AREA) {
            score += 20;
        }

        // Mild near-window heuristic
        if (Boolean.TRUE.equals(request.getNearWindow())) {
            if (table.getZone() == Zone.PATIO || table.getZone() == Zone.QUIET_AREA) {
                score += 8;
            }
        }

        // Mild boost for explicit zone choice
        if (request.getZone() != null && !request.getZone().isBlank()
                && table.getZone().name().equalsIgnoreCase(request.getZone())) {
            score += 15;
        }

        return score;
    }
}