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
public class Bed {

    @Id
    private String id;

    private Long number;
    private String status;
    private String idRoom;
    private Long idPatient;

    @ManyToOne
    private Room room;

    @Transient
    private Patient patient;

}
