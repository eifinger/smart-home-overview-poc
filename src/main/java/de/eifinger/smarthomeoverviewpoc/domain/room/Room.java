package de.eifinger.smarthomeoverviewpoc.domain.room;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class Room {
    @Id
    Long id;

    String name;
}
