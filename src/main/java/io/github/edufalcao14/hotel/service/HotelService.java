package io.github.edufalcao14.hotel.service;

import io.github.edufalcao14.hotel.dto.CheckInRequestDTO;
import io.github.edufalcao14.hotel.model.entity.Booking;
import io.github.edufalcao14.hotel.model.entity.BookingHistory;
import io.github.edufalcao14.hotel.model.entity.Client;

public interface HotelService {
    Booking checkIn(CheckInRequestDTO checkInRequestDTO);
    Client saveClient (Client client);
    Client getClient (Integer id);
    Booking verifyAccommodation (Integer id);
    void deleteClient (Integer id);
    Booking CheckOut (Integer Id);

    BookingHistory getBookingHistoryForClient(Integer id);
}
