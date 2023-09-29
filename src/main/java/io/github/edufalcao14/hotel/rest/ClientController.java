package io.github.edufalcao14.hotel.rest;

import io.github.edufalcao14.hotel.dto.CheckInRequestDTO;
import io.github.edufalcao14.hotel.model.entity.Booking;
import io.github.edufalcao14.hotel.model.entity.BookingHistory;
import io.github.edufalcao14.hotel.model.entity.Client;
import io.github.edufalcao14.hotel.model.entity.Room;
import io.github.edufalcao14.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clients")
public class ClientController {

    private final HotelService hotelService;
    @Autowired
    public ClientController(@RequestBody HotelService hotelService){
       this.hotelService=hotelService;
    }
    @PostMapping("/saveClient")
    @ResponseStatus(HttpStatus.CREATED)
    public Client saveClient(@RequestBody Client client ){
        return  hotelService.saveClient(client);
    }
    @PostMapping("/checkIn")
    @ResponseStatus(HttpStatus.CREATED)
    public Booking checkIn(@RequestBody CheckInRequestDTO checkInRequestDTO){
    return hotelService.checkIn(checkInRequestDTO);
    }

    @GetMapping("/getClient={id}")
    public Client verifyClient (@PathVariable Integer id){
        return hotelService.getClient(id);
    }
    @GetMapping("/getBookingHistory={id}")
    public BookingHistory getBookingHistoryForClient (@PathVariable Integer id){
        return hotelService.getBookingHistoryForClient(id);
    }
    @GetMapping("/verifyAccomodation={id}")
    public Booking verifyByRoom (@PathVariable Integer id){
       return hotelService.verifyAccommodation(id);
    }

    @DeleteMapping("/checkOut/{bookingId}")
    public void checkOut(@PathVariable Integer bookingId) {
        hotelService.CheckOut(bookingId);
    }
    @GetMapping("/checkFreeRooms")
    public List<Room> checkFreeRooms(){
        return hotelService.checkFreeRooms();
    }
}
