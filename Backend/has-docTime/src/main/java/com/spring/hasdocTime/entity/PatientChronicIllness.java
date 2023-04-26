package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="patient_chronic_illness")
//@JsonIdentityInfo(
//        scope = PatientChronicIllness.class,
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class PatientChronicIllness {

    @EmbeddedId
    private CompositeKeyPatientChronicIllness id;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name="patient_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"patientChronicIllness", "doctor", "admin", "appointmentData", "appointments", "symptoms"})
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @MapsId("chronicIllnessId")
    @JoinColumn(name="chronic_illness_id", referencedColumnName = "id")
    @JsonIgnoreProperties("patientChronicIllnesses")
    private ChronicIllness chronicIllness;

    @Column(name="years_of_illness")
    private int yearsOfIllness;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientChronicIllness that = (PatientChronicIllness) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
