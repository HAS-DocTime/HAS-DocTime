/**
 * The ChronicIllness class represents a chronic illness in the system.
 * It contains information about the illness's ID, name, and associated patient-illness relationships.
 */
package com.spring.hasdoctime.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "chronic_illness")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChronicIllness {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Please enter Symptom name")
    @Pattern(regexp = "^[a-zA-Z]+([. _-]?[a-zA-Z]+)*$", message = "Please enter valid name")
    private String name;

    @OneToMany(mappedBy = "chronicIllness", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "chronicIllness", allowSetters = true)
    @Valid
    private List<PatientChronicIllness> patientChronicIllnesses;

    /**
     * Checks if the current ChronicIllness object is equal to the given object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChronicIllness that = (ChronicIllness) o;
        return id == that.id;
    }

    /**
     * Generates a hash code value for the ChronicIllness object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
