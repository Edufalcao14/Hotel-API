package io.github.edufalcao14.hotel.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingHistory {
    @Id
    private Integer id;


    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Client client;

    @OneToMany( cascade = CascadeType.DETACH)
    private List<Booking> allBookings = new ArrayList<>();

    public BookingHistory(Client client){
        this.client = client;
    }

    public void addBookingToHistory(Booking booking){
        if (allBookings.contains(booking)) return;
       allBookings.add(booking);
    }
    @PrePersist
    public void prePersist(){



    }
}
