package com.restaurant_booking.controller;

import com.restaurant_booking.dto.RecommendationRequestDto;
import com.restaurant_booking.dto.RecommendationResponseDto;
import com.restaurant_booking.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommend")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public RecommendationResponseDto recommend(@RequestBody RecommendationRequestDto request) {
        return recommendationService.recommend(request);
    }
}