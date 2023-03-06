package de.eifinger.smarthomeoverviewpoc.controller;

import de.eifinger.smarthomeoverviewpoc.domain.home.HomeRepository;
import de.eifinger.smarthomeoverviewpoc.domain.home.Home;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

    private final HomeRepository homeRepository;

    public HomeController(HomeRepository homeRepository) {
        this.homeRepository = homeRepository;
    }

    @GetMapping("/home")
    public Flux<Home> getHomes() {
        return homeRepository.findAll();
    }

    @GetMapping("/home/{homeId}")
    public Mono<Home> getHome(@PathVariable String homeId) {
        return homeRepository.findById(homeId);
    }

    @PutMapping("/home")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Home> createHome(@RequestBody String homeName) {
        var newHome = Home.builder().name(homeName).build();
        return homeRepository.save(newHome);
    }
}