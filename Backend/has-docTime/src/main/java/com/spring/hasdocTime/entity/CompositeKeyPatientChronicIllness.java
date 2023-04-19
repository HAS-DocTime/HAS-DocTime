package com.spring.hasdocTime.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompositeKeyPatientChronicIllness implements Serializable {

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "chronic_illness_id")
    private int chronicIllnessId;
}
