package de.eifinger.smarthomeoverviewpoc.controller;

import de.eifinger.smarthomeoverviewpoc.domain.room.Room;
import de.eifinger.smarthomeoverviewpoc.domain.room.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/room")
    public Flux<Room> getRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/room/{roomId}")
    public Mono<Room> getRoom(@PathVariable Long roomId) {
        return roomRepository.findById(roomId);
    }

    @PutMapping("/home/{homeId}/room")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Room> createRoom(@PathVariable Long homeId, @RequestBody String roomName) {
        var newRoom = Room.builder().homeId(homeId).name(roomName).build();
        return roomRepository.save(newRoom);
    }
}