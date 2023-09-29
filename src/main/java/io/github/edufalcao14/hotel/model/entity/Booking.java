package io.github.edufalcao14.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonBackReference
    @ManyToOne
    private Client client;

    @Column(nullable = false,length = 10)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkInDate;

    @Column(nullable = false,length = 10)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private int numberOfRooms;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private double warrantyAmount;

    @Column(nullable = true)
    private double tariffPerNight;

    @Column(nullable = true,length = 200)
    private String observations;

    @Column(nullable = true)
    private boolean warrantyRetained;

    @Column(nullable = true)
    private int numberOfDays;

    @Column(nullable = true)
    private int reduction;

    @JsonManagedReference
    @OneToMany(mappedBy = "booking", cascade = CascadeType.DETACH)
    private List<Room> rooms = new ArrayList<>();

    @PrePersist
    public void prePersist(){
        LocalDate start = checkInDate;
        LocalDate end = checkOutDate;

        int days = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        setNumberOfDays(days);

        setWarrantyAmount(getWarrantyAmount());

        double totalAmount = tariffPerNight*numberOfDays;
        setTotalAmount(totalAmount);
        if (reduction != 0){
            setReduction(reduction);
            setTotalAmount(getTotalAmount()-reduction);
        }
    }
}
