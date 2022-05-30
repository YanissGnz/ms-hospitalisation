package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.Chambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
}
