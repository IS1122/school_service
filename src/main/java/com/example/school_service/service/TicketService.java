package com.example.school_service.service;

import com.example.school_service.entity.Role;
import com.example.school_service.entity.Ticket;
import com.example.school_service.entity.User;
import com.example.school_service.repository.TicketRepository;
import com.example.school_service.repository.UserRepository;
import com.example.school_service.response.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    UserRepository userRepository;

    public CustomResponse addTicket(Ticket ticket) {
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)) {
            Ticket newTicket = Ticket.builder().componentId(ticket.getComponentId())
                    .description(ticket.getDescription())
                    .laptopId(ticket.getLaptopId())
                    .status(ticket.getStatus())
                    .user(ticket.getUser())
                    .build();
            ticketRepository.save(newTicket);
            return CustomResponse.builder().message("Added Successfully").data(newTicket).build();
        } else {
            return CustomResponse.builder().message("You have no permission").build();
        }
    }

    public CustomResponse updateTicet(Ticket ticket, Integer ticketId) {
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)) {
            Optional<Ticket> ticketFound = ticketRepository.findById(ticketId);
            if (ticketFound.isPresent()) {
                ticketFound.get().setDescription(ticket.getDescription());
                ticketFound.get().setComponentId(ticket.getComponentId());
                ticketFound.get().setStatus(ticket.getStatus());
                ticketFound.get().setLaptopId(ticket.getLaptopId());
                ticketFound.get().setUser(ticket.getUser());
                ticketRepository.save(ticketFound.get());
                return CustomResponse.builder().data(ticketFound).message("Updated Successfully").build();
            } else {
                return CustomResponse.builder().message("Ticket with id: " + ticketId + " not found!").build();
            }
        } else {
            return CustomResponse.builder().message("You have no permission").build();
        }
    }

    public CustomResponse deleteTicket(Integer ticketId) {
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)) {
            Optional<Ticket> ticket = ticketRepository.findById(ticketId);
            if (ticket.isPresent()) {
                ticketRepository.delete(ticket.get());
                return CustomResponse.builder().data(ticket).message("Deleted Successfully").build();
            } else {
                return CustomResponse.builder().message("Ticket with id: " + ticketId + " not found!").build();
            }
        } else {
            return CustomResponse.builder().message("You have no permission").build();
        }
    }

    public CustomResponse getAll() {
        Optional<User> user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.isPresent() && user.get().getRole().equals(Role.ADMIN)) {
            return CustomResponse.builder().data(ticketRepository.findAll()).build();
        } else {
            return CustomResponse.builder().message("You have no permission").build();
        }
    }

    public CustomResponse getById(Integer ticketId) {
        return CustomResponse.builder().data(ticketRepository.findById(ticketId)).build();
    }

    public CustomResponse getTicketByStudentId(Integer studentId) {
        List<Ticket> ticketList = ticketRepository.findTicketByUserId(studentId);
        if (ticketList.size() > 0) {
            return CustomResponse.builder().data(ticketList).build();
        } else {
            return CustomResponse.builder().data(Collections.EMPTY_LIST).message("No data found!").build();
        }
    }
}
