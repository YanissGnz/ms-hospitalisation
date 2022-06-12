package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.Act;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ActRepository extends JpaRepository<Act, Long> {
    List<Act> getAllByIdPatient(Long idPatient);
    List<Act> getAllByIdStaff(Long idStaff);
}
