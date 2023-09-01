package io.github.edufalcao14.hotel;

import io.github.edufalcao14.hotel.model.entity.Client;
import io.github.edufalcao14.hotel.model.entity.Room;
import io.github.edufalcao14.hotel.model.repository.ClientRepository;
import io.github.edufalcao14.hotel.model.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HotelApplication {

    @Bean
    public CommandLineRunner run(@Autowired ClientRepository repository , RoomRepository roomRepository){
        Room room = Room.builder().roomNumber(201).floor("parquet").side("jardin").size(35).build();
        roomRepository.save(room);
        return args -> {
            Client client = Client.builder().id(1).name("eduardo").famillyName("sampaio").phoneNumber("+320465256614")
                    .checkInDate(LocalDate.now()).checkOutDate(LocalDate.of(2023,9,30))
                    .amountToPay(2300.00).warrantyAmount(100.00).numberRoom(room).build();
            repository.save(client);
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }

}
