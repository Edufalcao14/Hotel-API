package io.github.edufalcao14.hotel.model.repository;

import io.github.edufalcao14.hotel.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room,Integer> {
    @Query(
            value = "SELECT * FROM ROOM u WHERE u.occuped = false",
            nativeQuery = true)
    List<Room> getFreeRooms();

}
