package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.PlanedActMedicines;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanedActMedicineRepo extends JpaRepository<PlanedActMedicines, Long> {
}
