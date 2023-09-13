package io.github.edufalcao14.hotel.model.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings = new ArrayList<>();

    public BookingHistory(Client client){
        this.client = client;
    }


}
