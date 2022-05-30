package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.Hospitalisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface HospitalisationRepository extends JpaRepository<Hospitalisation, Long> {
}
