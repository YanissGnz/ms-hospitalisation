package com.example.mshospitalisation.entities;

import com.example.mshospitalisation.model.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Lit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long numero;
    private String state;

    private Long idChambre;

    @ManyToOne
    private Chambre chambre;

    @Transient
    private Patient patient;

    @OneToOne
    private Act act;

    @OneToOne
    private Hospitalisation hospitalisation;


}
