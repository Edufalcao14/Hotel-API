package io.github.edufalcao14.hotel.model.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

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
    private String name;

    @Column(nullable = false,length = 150)
    private String famillyName;

    @Column(nullable = true,length = 30)
    private String nationalNumber;

    @Column(nullable = true,length = 50)
    private String nationality;

    @Column(nullable = true,length = 50)
    private String email;

    @Column(nullable = false,length = 50)
    private String phoneNumber;

    @Column(nullable = false)
    private int tariffPerNight;

    @Column(nullable = false,length = 10)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate  checkInDate;

    @Column(nullable = false,length = 10)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOutDate;

    @Column(nullable = false)
    private double amountToPay;

    @Column(nullable = false)
    private double warrantyAmount;

    @Column(nullable = true,length = 200)
    private String observations;

    @Column(nullable = true)
    private boolean warrantyRetained;

    @ManyToOne
    @JoinColumn(name = "client_room")
    private Room numberRoom;

    @Column(nullable = true)
    private int numberOfDays;

    @Column(nullable = true)
    private int reduction;

    @PrePersist
    public void prePersist(){
        LocalDate start = LocalDate.now();
        setCheckInDate(start);
            LocalDate end = checkOutDate;
            Period period = Period.between(start,end);
            int days = period.getDays();
            setNumberOfDays(days);

            int totalprice = numberOfDays*tariffPerNight;
            setAmountToPay(totalprice);

            if (reduction != 0){
                totalprice-=reduction;
                setAmountToPay(totalprice);
            }
    }

}
