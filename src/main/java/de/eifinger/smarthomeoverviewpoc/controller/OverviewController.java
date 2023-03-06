package de.eifinger.smarthomeoverviewpoc.controller;

import de.eifinger.smarthomeoverviewpoc.controller.dto.OverviewDTO;
import de.eifinger.smarthomeoverviewpoc.controller.dto.RoomOverviewDTO;
import de.eifinger.smarthomeoverviewpoc.domain.home.HomeRepository;
import de.eifinger.smarthomeoverviewpoc.domain.room.RoomRepository;
import de.eifinger.smarthomeoverviewpoc.domain.thermostat.ThermostatRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@RestController
public class OverviewController {

    private final HomeRepository homeRepository;
    private final RoomRepository roomRepository;
    private final ThermostatRepository thermostatRepository;

    public OverviewController(HomeRepository homeRepository, RoomRepository roomRepository, ThermostatRepository thermostatRepository) {
        this.homeRepository = homeRepository;
        this.roomRepository = roomRepository;
        this.thermostatRepository = thermostatRepository;
    }

    @Operation(summary = "Get the overview for a home")
    @GetMapping("/overview/{homeId}")
    public Mono<OverviewDTO> overview(@Parameter(description = "The home id")  @PathVariable Long homeId) {
        return roomRepository.findByHomeId(homeId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(room ->
                        thermostatRepository.findByAssignedRoom(room.getId())
                                .collectList()
                                .map(thermostats -> new RoomOverviewDTO(room, thermostats)))
                .collectList()
                .flatMap(roomOverviewDTOs ->
                        homeRepository.findById(homeId)
                                .map(home -> new OverviewDTO(home, roomOverviewDTOs)));
    }
}
