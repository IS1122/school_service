package com.example.school_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ComponentId", referencedColumnName = "ID")
    private Component componentId;
    @OneToOne
    @JoinColumn(name = "LaptopId", referencedColumnName = "ID")
    private Laptop laptopId;
    private String description;
    private Status status;
    @OneToOne
    @JoinColumn(name = "UserId", referencedColumnName = "ID")
    private User user;
}
