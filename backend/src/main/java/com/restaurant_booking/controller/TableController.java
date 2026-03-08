package com.restaurant_booking.controller;

import com.restaurant_booking.dto.TableAvailabilityDto;
import com.restaurant_booking.model.RestaurantTable;
import com.restaurant_booking.repository.ReservationRepository;
import com.restaurant_booking.repository.RestaurantTableRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tables")
public class TableController {

    private final RestaurantTableRepository tableRepository;
    private final ReservationRepository reservationRepository;

    public TableController(RestaurantTableRepository tableRepository,
                           ReservationRepository reservationRepository) {
        this.tableRepository = tableRepository;
        this.reservationRepository = reservationRepository;
    }

    @GetMapping
    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }

    @GetMapping("/availability")
    public List<TableAvailabilityDto> getTableAvailability(
            @RequestParam LocalDateTime startTime,
            @RequestParam Integer durationHours
    ) {
        LocalDateTime endTime = startTime.plusHours(durationHours);

        return tableRepository.findAll().stream().map(table -> {
            boolean available = reservationRepository
                    .findOverlappingReservations(table.getId(), startTime, endTime)
                    .isEmpty();

            TableAvailabilityDto dto = new TableAvailabilityDto();
            dto.setTableId(table.getId());
            dto.setAvailable(available);
            return dto;
        }).toList();
    }
}