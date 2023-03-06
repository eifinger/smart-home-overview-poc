package de.eifinger.smarthomeoverviewpoc.domain.home;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface HomeRepository extends ReactiveCrudRepository<Home, String> {
}
