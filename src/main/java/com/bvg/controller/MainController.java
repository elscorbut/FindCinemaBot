package com.bvg.controller;

import com.bvg.dto.MovieDto;
import com.bvg.service.KpService;
import com.bvg.service.MovieService;
import com.bvg.type.MovieQbe;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class MainController {

    @Autowired
    KpService kpService;

    @Autowired
    MovieService movieService;

    @Operation(summary = "Поздоровайся с сервером")
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @Operation(summary = "Принудительная синхронизация списка фильмов")
    @GetMapping("/kp-wish-list")
    public void getKpWishList() throws InterruptedException {
        kpService.updateKpWishList();
    }

    @Operation(summary = "Получение отфильтрованного списка фильмов")
    @PostMapping("/filter")
    public List<MovieDto> getMovieList(
            @RequestBody @Parameter(description = "Фильтр для фильмов") MovieQbe qbe,
            @ParameterObject Pageable pageable) {

        return movieService.filter(qbe, pageable);
    }
}
