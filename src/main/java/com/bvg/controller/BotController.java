package com.bvg.controller;

import com.bvg.component.FindCinemaBot;
import com.bvg.service.TMDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BotController {

    @Autowired
    TMDBService tmdbService;

    @Autowired
    FindCinemaBot findCinemaBot;

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
