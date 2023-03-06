package de.eifinger.smarthomeoverviewpoc.domain.thermostat;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class Thermostat {

    @Id
    Long id;

    String name;

    float temperature;

    float humidity;
}
