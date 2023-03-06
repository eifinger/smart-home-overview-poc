package de.eifinger.smarthomeoverviewpoc.domain.thermostat;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ThermostatRepository extends ReactiveCrudRepository<Thermostat, Long> {

    Flux<Thermostat> findByAssignedRoom(Long roomId);

    Flux<Thermostat> findByAssignedHome(Long homeId);
}
