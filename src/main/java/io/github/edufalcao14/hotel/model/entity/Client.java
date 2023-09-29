package io.github.edufalcao14.hotel.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"phonenumber", "email"})})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false,length = 150)
    private String name;


    @Column(nullable = true,length = 30)
    private String documentNumber;

    @Column(nullable = true,length = 50)
    private String nationality;

    @Column(nullable = true,length = 50)
    private String email;

    @Column(nullable = false,length = 50)
    private String phoneNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.DETACH)
    private List<Booking> currentBookings = new ArrayList<>();

}
