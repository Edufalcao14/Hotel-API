package io.github.edufalcao14.hotel.service;

import io.github.edufalcao14.hotel.dto.CheckInRequestDTO;
import io.github.edufalcao14.hotel.model.entity.Booking;
import io.github.edufalcao14.hotel.model.entity.BookingHistory;
import io.github.edufalcao14.hotel.model.entity.Client;
import io.github.edufalcao14.hotel.model.entity.Room;

import java.util.List;

public interface HotelService {
    Booking checkIn(CheckInRequestDTO checkInRequestDTO);
    Client saveClient (Client client);
    Client getClient (Integer id);
    Booking verifyAccommodation (Integer id);
    void deleteClient (Integer id);
    void saveBookingToHistory(BookingHistory bookingHistory);

    void addRoomToBooking(Integer bookingId, Room room);

    List<Room> checkFreeRooms();

    void CheckOut (Integer Id);

    BookingHistory getBookingHistoryForClient(Integer id);
}
