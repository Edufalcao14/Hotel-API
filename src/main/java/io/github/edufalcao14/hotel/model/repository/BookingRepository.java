package io.github.edufalcao14.hotel.model.repository;

import io.github.edufalcao14.hotel.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Integer> {

}
