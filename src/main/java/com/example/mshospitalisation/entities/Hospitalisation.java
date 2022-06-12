package com.example.mshospitalisation.entities;

import com.example.mshospitalisation.model.Patient;
import com.example.mshospitalisation.model.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Date;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Hospitalisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    private Long idNurse;
    private Long idDoctor;
    @Column(unique = true)
    private Long idPatient;
    private String idBed;
    private String reason;
    @Temporal(TemporalType.DATE)
    private Date plannedOperationDate;
    private String other;

    @Transient
    private Staff nurse;

    @Transient
    private Staff doctor;

    @Transient
    private Patient patient;

    @Transient
    private Bed bed;


}
