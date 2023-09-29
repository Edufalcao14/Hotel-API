package io.github.edufalcao14.hotel.model.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false)
    private int roomNumber;

    @Column(nullable = false)
    private String floor;

    @Column(nullable = false)
    private String side;

    @Column(nullable = false)
    private int size;

    @Column(nullable = true)
    private boolean occuped=false;

    @Column(nullable = true)
    private String observations;

    @Column(nullable = false)
    private int tariffPerNight;
    @JsonBackReference
    @ManyToOne
    private Booking booking;

}
