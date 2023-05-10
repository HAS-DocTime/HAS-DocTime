package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import java.util.List;
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
    private String name;

    @OneToMany(mappedBy = "chronicIllness", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "chronicIllness")
    private List<PatientChronicIllness> patientChronicIllnesses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChronicIllness that = (ChronicIllness) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

