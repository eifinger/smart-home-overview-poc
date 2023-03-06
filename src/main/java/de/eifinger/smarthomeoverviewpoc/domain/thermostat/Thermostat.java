package de.eifinger.smarthomeoverviewpoc.domain.thermostat;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Value
@Builder
public class Thermostat {

    @Id
    Long id;

    String name;

    float temperature;

    float humidity;

    @Column("assigned_home")
    Long assignedHome;

    @Column("assigned_room")
    Long assignedRoom;

    public Thermostat assignToRoom(Long roomId) {
        return Thermostat.builder()
                .id(this.id)
                .name(this.name)
                .assignedHome(this.assignedHome)
                .temperature(this.temperature)
                .humidity(this.humidity)
                .assignedRoom(roomId)
                .build();
    }
}
