package com.Project.HotelManagement.controller;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.Project.HotelManagement.Service.GuestService;
import com.Project.HotelManagement.model.Guest;

import org.springframework.ui.Model;

import java.util.List;

@Controller
public class GuestController {

    private final GuestService service;

    public GuestController(GuestService service) {
        this.service = service;
    }

    // Home / booking form
    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("guest", new Guest());
        return "index";
    }

    // Handle booking form submit (server-side)
    @PostMapping("/book")
    public String bookGuest(@ModelAttribute Guest guest, Model model) {
        try {
            service.addGuest(guest);
            model.addAttribute("success", "Guest added successfully.");
        } catch (Exception e) {
            model.addAttribute("error", "Failed to add guest: " + e.getMessage());
        }
        model.addAttribute("guest", new Guest());
        return "index";
    }

    // One-click: show all guests
    @GetMapping("/guests")
    public String guestList(Model model)throws Exception {
        List<Guest> list = service.getAllGuests();
        model.addAttribute("guests", list);
        model.addAttribute("count", list.size());
        return "guestlist";
    }

    // Checkout endpoint (simple)
    @PostMapping("/checkout/{id}")
    public String checkout(@PathVariable Long id) {
        service.checkOut(id);
        return "redirect:/guests";
    }
}