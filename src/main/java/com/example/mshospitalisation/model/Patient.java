package com.example.mshospitalisation.model;

import com.example.mshospitalisation.entities.Lit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Patient {

    private Long id;
    private String last_name;
    private String first_name;
    @Transient
    private Lit lit;
}
