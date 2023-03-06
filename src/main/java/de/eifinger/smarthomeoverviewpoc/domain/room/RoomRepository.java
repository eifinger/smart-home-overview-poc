package de.eifinger.smarthomeoverviewpoc.domain.room;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface RoomRepository extends ReactiveCrudRepository<Room, Long> {
    Flux<Room> findByHomeId(Long homeId);
}
