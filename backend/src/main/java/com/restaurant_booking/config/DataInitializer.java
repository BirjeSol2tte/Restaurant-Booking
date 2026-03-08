package com.restaurant_booking.config;

import com.restaurant_booking.model.RestaurantTable;
import com.restaurant_booking.model.Zone;
import com.restaurant_booking.repository.RestaurantTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initTables(RestaurantTableRepository tableRepository) {
        return args -> {
            if (tableRepository.count() == 0) {
                List<RestaurantTable> tables = List.of(

                        // MAIN HALL
                        createTable("H1", 2, 170, 140, Zone.MAIN_HALL),
                        createTable("H2", 4, 270, 140, Zone.MAIN_HALL),
                        createTable("H3", 4, 370, 140, Zone.MAIN_HALL),
                        createTable("H4", 6, 220, 240, Zone.MAIN_HALL),
                        createTable("H5", 6, 340, 240, Zone.MAIN_HALL),

                        // QUIET AREA
                        createTable("Q1", 2, 620, 110, Zone.QUIET_AREA),
                        createTable("Q2", 2, 700, 110, Zone.QUIET_AREA),
                        createTable("Q3", 4, 660, 200, Zone.QUIET_AREA),

                        // PATIO
                        createTable("P1", 2, 140, 430, Zone.PATIO),
                        createTable("P2", 4, 250, 430, Zone.PATIO),
                        createTable("P3", 4, 360, 430, Zone.PATIO),
                        createTable("P4", 6, 470, 430, Zone.PATIO),

                        // PARTY ROOM
                        createTable("PR1", 10, 640, 430, Zone.PARTY_ROOM),
                        createTable("PR2", 12, 760, 430, Zone.PARTY_ROOM),

                        // PRIVATE ROOM
                        createTable("PV1", 8, 640, 300, Zone.PRIVATE_ROOM),
                        createTable("PV2", 10, 760, 300, Zone.PRIVATE_ROOM)
                );

                tableRepository.saveAll(tables);

                System.out.println("Restaurant tables initialized.");
            }
        };
    }

    private RestaurantTable createTable(String label, int capacity, int x, int y, Zone zone) {
        RestaurantTable t = new RestaurantTable();
        t.setTableLabel(label);
        t.setCapacity(capacity);
        t.setPosX(x);
        t.setPosY(y);
        t.setZone(zone);
        return t;
    }
}