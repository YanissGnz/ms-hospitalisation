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

    @Temporal(TemporalType.DATE)
    private Date date_debut;


    @Temporal(TemporalType.DATE)
    private Date date_fin;


    private Long idInfermier;
    private Long idMedcin;
    private Long idPatient;
    private Long idLit;

    @Transient
    private Staff infermier;

    @Transient
    private Staff medecin;

    @Transient
    private Patient patient;

    @OneToOne(mappedBy = "hospitalisation")
    private Lit lit;


}
