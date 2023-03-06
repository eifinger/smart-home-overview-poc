package de.eifinger.smarthomeoverviewpoc.controller;

import de.eifinger.smarthomeoverviewpoc.domain.room.Room;
import de.eifinger.smarthomeoverviewpoc.domain.room.RoomRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Operation(summary = "Get all rooms")
    @GetMapping("/room")
    public Flux<Room> getRooms() {
        return roomRepository.findAll();
    }

    @Operation(summary = "Get an existing room by id")
    @GetMapping("/room/{roomId}")
    public Mono<Room> getRoom(@PathVariable Long roomId) {
        return roomRepository.findById(roomId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Operation(summary = "Create a new room for a home")
    @PutMapping("/home/{homeId}/room")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Room> createRoom(@PathVariable Long homeId, @RequestBody String roomName) {
        var newRoom = Room.builder().homeId(homeId).name(roomName).build();
        return roomRepository.save(newRoom);
    }
}