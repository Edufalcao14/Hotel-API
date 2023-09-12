package io.github.edufalcao14.hotel.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false,length = 150)
    private String completName;


    @Column(nullable = true,length = 30)
    private String documentNumber;

    @Column(nullable = true,length = 50)
    private String nationality;

    @Column(nullable = true,length = 50)
    private String email;

    @Column(nullable = false,length = 50)
    private String phoneNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Accommodation> accommodations = new ArrayList<>();



}
