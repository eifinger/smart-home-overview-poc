package de.eifinger.smarthomeoverviewpoc.domain.thermostat;

import de.eifinger.smarthomeoverviewpoc.domain.room.Room;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Random;

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

    public float getHumidity() {
        return 10.0f + new Random().nextFloat() * (95.0f - 10.0f);
    }

    public float getTemperature() {
        return 5.0f + new Random().nextFloat() * (28.0f - 5.0f);
    }

    public Thermostat assignToRoom(Room room) {
        return Thermostat.builder()
                .id(this.id)
                .name(this.name)
                .assignedHome(this.assignedHome)
                .temperature(this.temperature)
                .humidity(this.humidity)
                .assignedRoom(room.getId())
                .assignedHome(room.getHomeId())
                .build();
    }
}
