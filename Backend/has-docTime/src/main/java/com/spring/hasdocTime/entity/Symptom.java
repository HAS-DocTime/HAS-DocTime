package com.spring.hasdocTime.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "symptom")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Symptom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @Pattern(regexp = "[a-zA-Z.-]+( [a-zA-Z.-]+)*", message = "Invalid input. Please enter a valid string with alphabetic characters, commas, periods, and dashes allowing spaces in between.")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_symptom",
            joinColumns = @JoinColumn(name = "symptom_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    @JsonIgnoreProperties(value = "symptoms", allowSetters = true)
    private List<User> users;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
    @JoinTable(
            name = "department_symptom",
            joinColumns = @JoinColumn(name = "symptom_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    @JsonIgnoreProperties(value = "symptoms", allowSetters = true)
    private List<Department> departments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "appointment_symptom",
            joinColumns = @JoinColumn(name = "symptom_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    @JsonIgnoreProperties(value = "symptoms", allowSetters = true)
    private List<Appointment> appointments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symptom that = (Symptom) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
