package io.github.edufalcao14.hotel.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String observations;
}
