package com.example.mshospitalisation.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    private String id;
    private String block;
    private int number;
    private String aile;
    private int bedCount;
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "room",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Collection<Bed> beds;


}
