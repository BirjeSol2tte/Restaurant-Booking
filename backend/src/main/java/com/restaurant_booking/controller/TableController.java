package com.restaurant_booking.controller;

import com.restaurant_booking.model.RestaurantTable;
import com.restaurant_booking.repository.RestaurantTableRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/tables")

public class TableController {

    private final RestaurantTableRepository tableRepository;

    public TableController(RestaurantTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @GetMapping
    public List<RestaurantTable> getAllTables() {
        return tableRepository.findAll();
    }
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    
}
