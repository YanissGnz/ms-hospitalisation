package com.example.mshospitalisation.entities;

import com.example.mshospitalisation.model.Consommable;
import com.example.mshospitalisation.model.Medicament;
import com.example.mshospitalisation.model.Patient;
import com.example.mshospitalisation.model.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Act {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomAct;

    private Long idActType;
    private Long idLit;
    private Long idStaff;
    private Long idPatient;

    @OneToOne(mappedBy = "act")
    private ActType actType;

    @Transient
    private Staff staff;

    @Transient
    private Patient patient;

    @OneToOne(mappedBy = "act")
    private Lit lit;


    private String description;

    @Temporal(TemporalType.DATE)
    private Date date_Act;




    @Transient
    private List<Medicament> les_medicaments;

    @Transient
    private List<Consommable> les_consommables;



    private  String type;
    private String rythme;
    private  String contenance;
    private Long durée;
    private String Ablation;
    private String siège;
    private String évolution;
    private String sonde_vésicale;




}
