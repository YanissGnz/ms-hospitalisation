package com.example.mshospitalisation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

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
    @Temporal(TemporalType.DATE)
    private Date date;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Act act;
}
