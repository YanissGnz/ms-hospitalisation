package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.ActType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ActTypeRepository extends JpaRepository<ActType , Long> {
}
