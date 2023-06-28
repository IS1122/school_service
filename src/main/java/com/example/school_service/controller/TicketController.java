package com.example.school_service.controller;

import com.example.school_service.entity.Component;
import com.example.school_service.entity.Ticket;
import com.example.school_service.response.CustomResponse;
import com.example.school_service.service.ComponentService;
import com.example.school_service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("/getAll")
    public CustomResponse getAll(){
        return ticketService.getAll();
    }
    @GetMapping("/getById/{ticketId}")
    public CustomResponse getById(@PathVariable Integer ticketId){
        return ticketService.getById(ticketId);
    }
    @PostMapping("/addTicket")
    public CustomResponse addComponent(@RequestBody Ticket ticket){
        return ticketService.addTicket(ticket);
    }
    @DeleteMapping("/deleteTicket/{ticketId}")
    public CustomResponse deleteComponent(@PathVariable Integer ticketId){
        return ticketService.deleteTicket(ticketId);
    }
}
