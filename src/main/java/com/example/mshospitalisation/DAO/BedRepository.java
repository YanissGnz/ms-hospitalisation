package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.Bed;
import com.example.mshospitalisation.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BedRepository extends JpaRepository<Bed, String> {
    Bed findByNumberAndRoom(Long number, Room room);
}
