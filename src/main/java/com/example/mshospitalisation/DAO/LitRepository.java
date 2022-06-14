package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.Lit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LitRepository extends JpaRepository<Lit , Long> {
}
