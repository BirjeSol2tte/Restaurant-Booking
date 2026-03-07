package com.restaurant_booking.service;

import com.restaurant_booking.dto.RecommendationRequestDto;
import com.restaurant_booking.dto.RecommendationResponseDto;
import com.restaurant_booking.model.RestaurantTable;
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
                // capacity filter
                .filter(t -> t.getCapacity() >= request.getGroupSize())
                // availability filter (no overlap)
                .filter(t -> reservationRepository.findOverlappingReservations(t.getId(), start, end).isEmpty())
                .toList();

        if (candidates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No available tables for selected time.");
        }

        // Pick best table:
        // 1) smallest capacity that still fits (least wasted seats)
        // 2) apply small preference boost using x/y heuristics (optional)
        // 3) tie-breaker by id
        RestaurantTable best = candidates.stream()
                .min(Comparator
                        .comparingInt((RestaurantTable t) -> (t.getCapacity() - request.getGroupSize()))
                        .thenComparingInt(t -> -preferenceScore(t, request))
                        .thenComparingLong(RestaurantTable::getId)
                )
                .orElseThrow();

        RecommendationResponseDto response = new RecommendationResponseDto();
        response.setTableId(best.getId());
        response.setCapacity(best.getCapacity());
        response.setStartTime(start);
        response.setEndTime(end);

        return response;
    }

    // Optional heuristic preferences (since we don’t have explicit "window/quiet" fields yet)
    private int preferenceScore(RestaurantTable t, RecommendationRequestDto request) {
        int score = 0;

        // Example heuristic: "nearWindow" prefers larger xPosition (right side)
        if (Boolean.TRUE.equals(request.getNearWindow())) {
            if (t.getXPosition() >= 300) score += 1;
        }

        // Example heuristic: "quietArea" prefers larger yPosition (bottom side)
        if (Boolean.TRUE.equals(request.getQuietArea())) {
            if (t.getYPosition() >= 250) score += 1;
        }

        return score;
    }
}