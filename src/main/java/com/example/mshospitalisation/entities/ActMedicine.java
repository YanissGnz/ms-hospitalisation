package com.example.mshospitalisation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ActMedicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  Long idMedicine;
    private String name;
    private int qte;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Act act;
}
