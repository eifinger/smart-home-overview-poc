package de.eifinger.smarthomeoverviewpoc.controller;

import de.eifinger.smarthomeoverviewpoc.domain.thermostat.Thermostat;
import de.eifinger.smarthomeoverviewpoc.domain.thermostat.ThermostatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class ThermostatController {

    private final ThermostatRepository thermostatRepository;

    public ThermostatController(ThermostatRepository thermostatRepository) {
        this.thermostatRepository = thermostatRepository;
    }

    @GetMapping("/thermostat")
    public Flux<Thermostat> getThermostats(@RequestParam Optional<Long> roomId, @RequestParam Optional<Long> homeId) {

        return roomId
                .map(thermostatRepository::findByAssignedRoom)
                .orElse(
                    homeId
                            .map(thermostatRepository::findByAssignedHome)
                            .orElse(thermostatRepository.findAll())
                );
    }

    @GetMapping("/thermostat/{thermostatId}")
    public Mono<Thermostat> getThermostat(@PathVariable Long thermostatId) {
        return thermostatRepository.findById(thermostatId);
    }

    @PutMapping("/thermostat")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Thermostat> createThermostat(@RequestBody String thermostatName) {
        var newThermostat = Thermostat.builder().name(thermostatName).build();
        return thermostatRepository.save(newThermostat);
    }

    @PostMapping("/thermostat/{thermostatId}/assignRoom")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Thermostat> assignThermostatToRoom(@PathVariable Long thermostatId, @RequestBody Long roomId) {
        return thermostatRepository.findById(thermostatId)
                .flatMap(thermostat -> thermostatRepository.save(thermostat.assignToRoom(roomId)));

    }
}