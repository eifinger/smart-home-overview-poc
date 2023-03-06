package de.eifinger.smarthomeoverviewpoc.controller;

import de.eifinger.smarthomeoverviewpoc.domain.home.HomeRepository;
import de.eifinger.smarthomeoverviewpoc.domain.home.Home;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

    private final HomeRepository homeRepository;

    public HomeController(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @Operation(summary = "Get all homes")
    @GetMapping("/home")
    public Flux<Home> getHomes() {
        return homeRepository.findAll();
    }

    @Operation(summary = "Get an existing home by id")
    @GetMapping("/home/{homeId}")
    public Mono<Home> getHome(@PathVariable Long homeId) {
        return homeRepository.findById(homeId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Operation(summary = "Create a new home")
    @PutMapping("/home")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Home> createHome(@RequestBody String homeName) {
        var newHome = Home.builder().name(homeName).build();
        return homeRepository.save(newHome);
    }
}