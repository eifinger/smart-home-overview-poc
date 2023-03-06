package de.eifinger.smarthomeoverviewpoc.controller.dto;

import de.eifinger.smarthomeoverviewpoc.domain.home.Home;

import java.util.List;

public record OverviewDTO(Home home, List<RoomOverviewDTO> roomOverviews) {}
