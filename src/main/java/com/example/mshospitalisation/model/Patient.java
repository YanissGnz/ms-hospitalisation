package com.example.mshospitalisation.model;

import com.example.mshospitalisation.entities.Bed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Patient {
    private Long id;
    private String lastname;
    private String firstname;
}
