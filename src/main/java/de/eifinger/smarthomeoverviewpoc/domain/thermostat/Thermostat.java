package de.eifinger.smarthomeoverviewpoc.domain.thermostat;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

import java.util.Random;

@Value
@Builder
public class Thermostat {

    @Transient
    private final Random random = new Random();

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
        return 10.0f + random.nextFloat() * (95.0f - 10.0f);
    }

    public float getTemperature() {
        return 5.0f + random.nextFloat() * (28.0f - 5.0f);
    }

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
