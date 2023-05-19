package com.spring.hasdocTime.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class CompositeKeyPatientChronicIllness implements Serializable {

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "chronic_illness_id")
    private int chronicIllnessId;



    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getChronicIllnessId() {
        return chronicIllnessId;
    }

    public void setChronicIllnessId(int chronicIllnessId) {
        this.chronicIllnessId = chronicIllnessId;
    }
}
