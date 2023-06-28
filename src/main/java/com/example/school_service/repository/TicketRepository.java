package com.example.school_service.repository;

import com.example.school_service.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    @Query(value = "select ticket from Ticket ticket where ticket.user.id=?1")
    List<Ticket> findTicketByUserId(Integer userId);
}
