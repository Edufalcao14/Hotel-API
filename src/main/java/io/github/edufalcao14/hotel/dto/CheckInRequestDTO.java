package io.github.edufalcao14.hotel.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.edufalcao14.hotel.model.entity.Booking;
import io.github.edufalcao14.hotel.model.entity.Client;
import io.github.edufalcao14.hotel.model.entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CheckInRequestDTO {

    private Integer clientId;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkInDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOutDate;

    private int numberOfRooms;
    private Double warrantyAmount;
    private String observations;
    private Integer reduction;

    private Integer roomId;

    private  List<Integer> roomIds;



    public Booking convertToAccomodation(Client client){
        Booking newBooking = new Booking();
        newBooking.setCheckInDate(checkInDate);
        newBooking.setCheckOutDate(checkOutDate);
        newBooking.setWarrantyAmount(warrantyAmount);
        newBooking.setObservations(observations);
        newBooking.setReduction(reduction);
        newBooking.setClient(client);
        newBooking.setRooms(new ArrayList<>());
        newBooking.setNumberOfRooms(numberOfRooms);

        return newBooking;
    }
}
