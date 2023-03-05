package de.eifinger.smarthomeoverviewpoc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OverviewController {

    @GetMapping("/overview")
    public String overview() {
        return "This is where your overview will be";
    }
}
