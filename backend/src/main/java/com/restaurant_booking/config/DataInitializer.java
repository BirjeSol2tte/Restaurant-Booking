package com.restaurant_booking.config;

import com.restaurant_booking.model.RestaurantTable;
import com.restaurant_booking.repository.RestaurantTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RestaurantTableRepository tableRepository;

    public DataInitializer(RestaurantTableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public void run(String... args) {
        if (tableRepository.count() > 0) return;

        List<RestaurantTable> tables = List.of(
                create(2, 80, 80),
                create(2, 200, 80),
                create(4, 80, 180),
                create(4, 200, 180),
                create(6, 80, 300),
                create(6, 240, 300),
                create(8, 420, 120)
        );

        tableRepository.saveAll(tables);
    }

    private RestaurantTable create(int capacity, int x, int y) {
        RestaurantTable t = new RestaurantTable();
        t.setCapacity(capacity);
        t.setXPosition(x);
        t.setYPosition(y);
        return t;
    }
}