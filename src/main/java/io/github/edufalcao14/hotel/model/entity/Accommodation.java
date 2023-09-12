package io.github.edufalcao14.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    private Client client;

    @Column(nullable = false)
    private int tariffPerNight;

    @Column(nullable = false,length = 10)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkInDate;

    @Column(nullable = false,length = 10)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private double warrantyAmount;

    @Column(nullable = true,length = 200)
    private String observations;

    @Column(nullable = true)
    private boolean warrantyRetained;

    @Column(nullable = true)
    private int numberOfDays;

    @Column(nullable = true)
    private int reduction;

    @OneToOne
    private Room room;
    @PrePersist
    public void prePersist(){
        LocalDate start = checkInDate;
        LocalDate end = checkOutDate;

        int days = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        setNumberOfDays(days);

        int totalprice = numberOfDays*tariffPerNight;

        setTotalAmount(totalprice);
        setWarrantyAmount(getWarrantyAmount());
        if (reduction != 0){
            setReduction(reduction);
            totalprice-=reduction;
            setTotalAmount(totalprice);
        }
    }
}
