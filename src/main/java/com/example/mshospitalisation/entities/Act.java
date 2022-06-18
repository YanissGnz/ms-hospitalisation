package com.example.mshospitalisation.entities;

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
    private String actType;
    private String idBed;
    private Long idStaff;
    private Long idPatient;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToMany(mappedBy="act")
    private List<ActMedicine> medicines;

    @OneToMany(mappedBy="act")
    private List<ActConsumable> consumables;

    @Transient
    private Staff staff;

    @Transient
    private Patient patient;

    @Transient
    private Bed bed;

}
