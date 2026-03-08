package com.restaurant_booking.service;

import com.restaurant_booking.dto.DishOfTheDayDto;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class MenuService {

    public DishOfTheDayDto getDishOfTheDay(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();

        DishOfTheDayDto dish = new DishOfTheDayDto();

        switch (day) {
            case MONDAY -> {
                dish.setName("Salmon Soup");
                dish.setDescription("Creamy salmon soup with potatoes and dill.");
            }
            case TUESDAY -> {
                dish.setName("Chicken Pasta");
                dish.setDescription("Pasta with grilled chicken and parmesan sauce.");
            }
            case WEDNESDAY -> {
                dish.setName("Mushroom Risotto");
                dish.setDescription("Creamy risotto with mushrooms and herbs.");
            }
            case THURSDAY -> {
                dish.setName("Beef Stew");
                dish.setDescription("Slow-cooked beef stew with vegetables.");
            }
            case FRIDAY -> {
                dish.setName("Grilled Salmon");
                dish.setDescription("Grilled salmon with mashed potatoes and lemon sauce.");
            }
            case SATURDAY -> {
                dish.setName("BBQ Pork Ribs");
                dish.setDescription("Tender BBQ pork ribs with roasted potatoes.");
            }
            case SUNDAY -> {
                dish.setName("Roast Chicken");
                dish.setDescription("Roast chicken with seasonal vegetables.");
            }
        }

        return dish;
    }
}