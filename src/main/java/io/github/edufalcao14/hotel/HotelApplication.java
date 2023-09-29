package io.github.edufalcao14.hotel;

import io.github.edufalcao14.hotel.model.entity.Booking;
import io.github.edufalcao14.hotel.model.entity.Client;
import io.github.edufalcao14.hotel.model.entity.Room;
import io.github.edufalcao14.hotel.model.repository.BookingRepository;
import io.github.edufalcao14.hotel.model.repository.ClientRepository;
import io.github.edufalcao14.hotel.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class HotelApplication {

    @Bean
    public CommandLineRunner run(@Autowired ClientRepository repository ,@Autowired RoomRepository roomRepository ,@Autowired BookingRepository bookingRepository){
        Room room = Room.builder().roomNumber(201).floor("parquet").side("jardin").size(35).tariffPerNight(100).build();
        Room room2 = Room.builder().roomNumber(202).floor("tapis").side("rue").size(30).tariffPerNight(150).build();
        Room room3 = Room.builder().roomNumber(403).floor("tapis VIP").side("jardin").size(35).tariffPerNight(90).build();
        roomRepository.save(room);
        roomRepository.save(room2);
        roomRepository.save(room3);
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(room);
        rooms.add(room2);
        rooms.add(room3);
        Client client = Client.builder().id(1).name("eduardo").phoneNumber("+320465256614").build();
        Booking booking = Booking.builder().client(client)
                .checkInDate(LocalDate.now())
                .checkOutDate(LocalDate.of(2023,9,30))
                .warrantyAmount(100.00)
                .reduction(50)
                .build();
        return args -> {
            repository.save(client);
            bookingRepository.save(booking);
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }

}
