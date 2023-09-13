package io.github.edufalcao14.hotel.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.edufalcao14.hotel.model.entity.Booking;
import io.github.edufalcao14.hotel.model.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CheckInRequestDTO {

    private Integer clientId;
    private Integer tariffPerNight;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkInDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOutDate;

    private Double warrantyAmount;
    private String observations;
    private Integer reduction;
    private int roomNumber;

    private Integer roomId;

    public Booking convertToAccomodation(Client client){
        Booking newBooking = new Booking();
        newBooking.setTariffPerNight(tariffPerNight);
        newBooking.setCheckInDate(checkInDate);
        newBooking.setCheckOutDate(checkOutDate);
        newBooking.setWarrantyAmount(warrantyAmount);
        newBooking.setObservations(observations);
        newBooking.setReduction(reduction);
        newBooking.setClient(client);

        return newBooking;
    }
}
