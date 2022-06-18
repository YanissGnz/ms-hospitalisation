package com.example.mshospitalisation.entities;

import com.example.mshospitalisation.model.Medicine;
import com.example.mshospitalisation.model.Patient;
import com.example.mshospitalisation.model.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanedAct {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idStaff;
    private Long idPatient;
    private String idBed;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    private String actType;
    private String description;
    private boolean isDone;


    @OneToMany(mappedBy="planedAct")
    private List<PlanedActMedicines> medicines;

    @OneToMany(mappedBy="planedAct")
    private List<PlanedActConsumable> consumables;

    @Transient
    Patient patient;

    @Transient
    Staff staff;
}