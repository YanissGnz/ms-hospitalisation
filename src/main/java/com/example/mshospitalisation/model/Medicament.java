package com.example.mshospitalisation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicament {

    private Long id;
    private String nom;
    private String qte;

}
