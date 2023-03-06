package de.eifinger.smarthomeoverviewpoc.domain.room;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Value
@Builder
public class Room {
    @Id
    Long id;

    String name;

    @Column("home_id")
    Long homeId;
}
