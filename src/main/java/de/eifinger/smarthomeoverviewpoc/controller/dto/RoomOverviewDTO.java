package de.eifinger.smarthomeoverviewpoc.controller.dto;

import de.eifinger.smarthomeoverviewpoc.domain.room.Room;
import de.eifinger.smarthomeoverviewpoc.domain.thermostat.Thermostat;

import java.util.List;

public record RoomOverviewDTO(Room room, List<Thermostat> thermostats) {}