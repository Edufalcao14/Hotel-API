package io.github.edufalcao14.hotel.model.repository;

import io.github.edufalcao14.hotel.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Integer> {



}
