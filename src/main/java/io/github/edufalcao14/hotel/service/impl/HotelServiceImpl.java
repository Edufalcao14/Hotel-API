package io.github.edufalcao14.hotel.service.impl;

import io.github.edufalcao14.hotel.dto.CheckInRequestDTO;
import io.github.edufalcao14.hotel.model.entity.Booking;
import io.github.edufalcao14.hotel.model.entity.BookingHistory;
import io.github.edufalcao14.hotel.model.entity.Client;
import io.github.edufalcao14.hotel.model.entity.Room;
import io.github.edufalcao14.hotel.model.repository.BookingHistoryRepository;
import io.github.edufalcao14.hotel.model.repository.BookingRepository;
import io.github.edufalcao14.hotel.model.repository.ClientRepository;
import io.github.edufalcao14.hotel.model.repository.RoomRepository;
import io.github.edufalcao14.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service

public class HotelServiceImpl implements HotelService {

    private final ClientRepository clientrepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingHistoryRepository bookingHistoryRepository;
    @Autowired
    public HotelServiceImpl(ClientRepository clientrepository , BookingRepository bookingRepository,
                            RoomRepository roomRepository , BookingHistoryRepository bookingHistoryRepository){
        this.clientrepository=clientrepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.bookingHistoryRepository = bookingHistoryRepository;
    }




    @Override
    @Transactional
    public Booking checkIn(CheckInRequestDTO checkInRequestDTO) {
        //verify if client exist
        Client client = clientrepository.
                findById(checkInRequestDTO.getClientId()).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        //verify if bookingHistory exist
        BookingHistory bookingHistory = bookingHistoryRepository
                .findById(client.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // Create a new booking for the client
        Booking booking = checkInRequestDTO.convertToAccomodation(client);

        // Initialize a list of rooms to be added to the booking
        List<Room> roomsToAdd = new ArrayList<>();

        // Loop through the room IDs provided in the request
        for (Integer roomId : checkInRequestDTO.getRoomIds()) {
            // Verify if room exists
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            // Verify if the room is free
            if (room.isOccuped()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Room is already occupied");
            }
            //Total amount calcul

            // Add the room to the booking and mark it as occupied
            roomsToAdd.add(room);
            room.setOccuped(true);
            room.setBooking(booking);
            roomRepository.save(room);
        }
        int tariffPerNight = booking.getRooms()
                .stream()
                .map(e -> e.getTariffPerNight())
                .reduce((a , b) -> a + b )
                .orElse(0);
        booking.setTariffPerNight(tariffPerNight);
        booking.getRooms().addAll(roomsToAdd);
        client.getCurrentBookings().add(booking);



        bookingHistoryRepository.save(bookingHistory);
        return bookingRepository.save(booking);
    }
    @Override
    public void addRoomToBooking(Integer bookingId, Room room) {
        Booking booking = bookingRepository
                .findById(bookingId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        booking.getRooms().add(room);
    }

    @Override
    public List<Room> checkFreeRooms() {
        return roomRepository.getFreeRooms();
    }

    @Override
    public Client saveClient(Client client) {
        BookingHistory bookingHistory =  new BookingHistory(client);
        bookingHistoryRepository.save(bookingHistory);
        return  clientrepository.save(client);
    }

    @Override
    public Client getClient(Integer id) {
        return clientrepository.
                findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Override
    public Booking verifyAccommodation(Integer id) {

        return  bookingRepository.
                findById(id).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Override
    public void deleteClient(Integer id) {
        Client clientToRemove = clientrepository
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (clientToRemove.getCurrentBookings() != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        bookingHistoryRepository.deleteById(id);
        clientrepository.delete(clientToRemove);
    }

    public void saveBookingToHistory(BookingHistory bookingHistory){

        bookingHistoryRepository.save(bookingHistory);
    }


    @Override
    public void CheckOut(Integer id) {
        // Verify if booking exists
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found"));

        // Get the client associated with the booking
        Client client = clientrepository.findById(booking.getClient().getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND , "Client Not found"));

        //Verify if booking  History exists
        BookingHistory bookingHistory = bookingHistoryRepository
                .findById(client.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking History not found"));

        // save booking in History
        bookingHistory.setClient(client);
        bookingHistory.addBookingToHistory(booking);
        saveBookingToHistory(bookingHistory);

        // Remove booking from the client's current bookings List
        client.getCurrentBookings().remove(booking);
        booking.setClient(null);

        // Save the updated client (without the booking)
        clientrepository.save(client);

        // Clear the association between the booking and the rooms
        for (Room room: booking.getRooms()) {
            room.setBooking(null);
            room.setOccuped(false);
            roomRepository.save(room);
        }


        booking.setRooms(null);
    }
    @Override
    public BookingHistory getBookingHistoryForClient(Integer id) {

        return bookingHistoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
