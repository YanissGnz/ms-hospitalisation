package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.ActMedicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActMedicineRepository extends JpaRepository<ActMedicine,Long> {
    @Query("SELECT m.name , m.date, SUM(m.qte) FROM ActMedicine AS m  GROUP BY m.date ORDER BY m.date ASC")
    List<Object[]> medicineStats();
}
