package de.eifinger.smarthomeoverviewpoc.domain.home;

import lombok.*;
import org.springframework.data.annotation.Id;

@Value
@Builder
public class Home {

    @Id
    Long id;

    String name;
}
