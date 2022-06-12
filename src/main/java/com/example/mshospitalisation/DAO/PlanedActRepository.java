package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.PlanedAct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanedActRepository extends JpaRepository<PlanedAct,Long> {
    List<PlanedAct> getAllByIdStaff(Long idStaff);
}
