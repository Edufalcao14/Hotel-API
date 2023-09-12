package io.github.edufalcao14.hotel.rest;

import io.github.edufalcao14.hotel.dto.CheckInRequestDTO;
import io.github.edufalcao14.hotel.model.entity.Accommodation;
import io.github.edufalcao14.hotel.model.entity.Client;
import io.github.edufalcao14.hotel.model.entity.Room;
import io.github.edufalcao14.hotel.model.repository.ClientRepository;
import io.github.edufalcao14.hotel.model.repository.AccomodationRepository;
import io.github.edufalcao14.hotel.model.repository.RoomRepository;
import io.github.edufalcao14.hotel.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public Accommodation checkIn(@RequestBody CheckInRequestDTO checkInRequestDTO){
    return hotelService.checkIn(checkInRequestDTO);
    }

    @GetMapping("/getClient={id}")
    public Client verifyClient (@PathVariable Integer id){
        return hotelService.getClient(id);
    }
    @GetMapping("/verifyByRoom={id}")
    public Accommodation verifyByRoom (@PathVariable Integer id){
       return hotelService.verifyByRoom(id);
    }

    @DeleteMapping("/checkOut={id}")
    public void deleteClient (@PathVariable Integer id){
        hotelService.deleteClient(id);
    }


}
