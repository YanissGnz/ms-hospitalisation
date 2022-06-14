package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.Hospitalisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface HospitalisationRepository extends JpaRepository<Hospitalisation, Long> {
    Hospitalisation getHospitalisationByIdPatient(Long idPatient);
    @Query("select count(h.id) , h.reason from Hospitalisation h group by h.reason")
    List<Object[]> countHospitalisationByreson();
}
