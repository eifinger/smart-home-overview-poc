package de.eifinger.smarthomeoverviewpoc.domain.room;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface RoomRepository extends ReactiveCrudRepository<Room, Long> {
}
