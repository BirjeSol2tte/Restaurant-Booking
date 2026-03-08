package com.restaurant_booking.controller;

import com.restaurant_booking.dto.DishOfTheDayDto;
import com.restaurant_booking.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping("/dish-of-the-day")
    public DishOfTheDayDto getDishOfTheDay(@RequestParam LocalDate date) {
        return menuService.getDishOfTheDay(date);
    }
}