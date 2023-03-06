package de.eifinger.smarthomeoverviewpoc.controller;

import de.eifinger.smarthomeoverviewpoc.domain.thermostat.Thermostat;
import de.eifinger.smarthomeoverviewpoc.domain.thermostat.ThermostatRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Get all thermostats. Optionally filter by room and/or home.")
    @GetMapping("/thermostat")
    public Flux<Thermostat> getThermostats(
            @Parameter(description = "Filter by room id") @RequestParam Optional<Long> roomId,
            @Parameter(description = "Filter home id") @RequestParam Optional<Long> homeId
    ) {

        return roomId
                .map(thermostatRepository::findByAssignedRoom)
                .orElse(
                    homeId
                            .map(thermostatRepository::findByAssignedHome)
                            .orElse(thermostatRepository.findAll())
                );
    }

    @Operation(summary = "Get an existing thermostat by id")
    @GetMapping("/thermostat/{thermostatId}")
    public Mono<Thermostat> getThermostat(
            @Parameter(description = "The thermostat id")  @PathVariable Long thermostatId
    ) {
        return thermostatRepository.findById(thermostatId);
    }

    @Operation(summary = "Create a new thermostat")
    @PutMapping("/thermostat")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Thermostat> createThermostat(
            @Parameter(description = "The thermostat name") @RequestBody String thermostatName
    ) {
        var newThermostat = Thermostat.builder().name(thermostatName).build();
        return thermostatRepository.save(newThermostat);
    }

    @Operation(summary = "Assign an existing thermostat to a room")
    @PostMapping("/thermostat/{thermostatId}/assignRoom")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Thermostat> assignThermostatToRoom(
            @Parameter(description = "The thermostat id") @PathVariable Long thermostatId,
            @Parameter(description = "The room id") @RequestBody Long roomId
    ) {
        return thermostatRepository.findById(thermostatId)
                .flatMap(thermostat -> thermostatRepository.save(thermostat.assignToRoom(roomId)));

    }
}