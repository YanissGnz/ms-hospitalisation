package com.example.mshospitalisation.DAO;

import com.example.mshospitalisation.entities.ActConsumable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActConsumableRepository extends JpaRepository<ActConsumable, Long> {

    @Query("SELECT c.name , c.date, SUM(c.qte) FROM ActConsumable AS c  GROUP BY c.date ORDER BY c.date ASC")
    List<Object[]> consumableStat();
}
