package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChambreRepository extends JpaRepository<Room, String> {
    Room getByNumberAndAileAndBlock(int number, String aile, String block);
}
