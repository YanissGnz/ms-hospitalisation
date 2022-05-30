package com.example.mshospitalisation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consommable {

    private Long id;
    private String nom_cosommable;
    private String qte_cosommable;
}
