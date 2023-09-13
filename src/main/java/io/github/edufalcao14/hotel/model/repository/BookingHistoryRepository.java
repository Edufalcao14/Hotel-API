package io.github.edufalcao14.hotel.model.repository;

import io.github.edufalcao14.hotel.model.entity.Booking;
import io.github.edufalcao14.hotel.model.entity.BookingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingHistoryRepository extends JpaRepository<BookingHistory,Integer> {

}
