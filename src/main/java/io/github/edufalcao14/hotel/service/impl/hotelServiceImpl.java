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
import org.springframework.web.server.ResponseStatusException;

@Service

public class hotelServiceImpl implements HotelService {


    private final ClientRepository clientrepository;
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    private final BookingHistoryRepository bookingHistoryRepository;

    @Autowired
    public hotelServiceImpl(ClientRepository clientrepository , BookingRepository bookingRepository,
                            RoomRepository roomRepository , BookingHistoryRepository bookingHistoryRepository){
        this.clientrepository=clientrepository;
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.bookingHistoryRepository = bookingHistoryRepository;
    }
    @Override
    public Booking checkIn(CheckInRequestDTO checkInRequestDTO) {
        //verify if client exist
        Client client = clientrepository.
                findById(checkInRequestDTO.getClientId()).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        //verify if room exist
        Room room = roomRepository.
                findById(checkInRequestDTO.getRoomId()).
                orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //verify if room is free
        if (room.isOccuped()==true) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        //verify if bookingHistory exist
       BookingHistory bookingHistory = bookingHistoryRepository
                .findById(client.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));


        Booking booking = checkInRequestDTO.convertToAccomodation(client);
        booking.setRoom(room);
        room.setOccuped(true);
        bookingHistory.getBookings().add(booking);

        roomRepository.save(room);
        bookingHistoryRepository.save(bookingHistory);

        return bookingRepository.save(booking);
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
        if (!clientToRemove.getCurrentBookings().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        bookingHistoryRepository.deleteById(id);
        clientrepository.delete(clientToRemove);
    }


    @Override
    public Booking CheckOut(Integer Id) {


        return null;
    }

    @Override
    public BookingHistory getBookingHistoryForClient(Integer id) {

        return bookingHistoryRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
