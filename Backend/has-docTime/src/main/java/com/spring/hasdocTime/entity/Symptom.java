package com.spring.hasdocTime.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * The Symptom class represents a symptom in the system.
 * It contains information about the symptom's ID, name, associated users,
 * associated departments, and associated appointments.
 */
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
    private Set<Department> departments = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "appointment_symptom",
            joinColumns = @JoinColumn(name = "symptom_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    @JsonIgnoreProperties(value = "symptoms", allowSetters = true)
    private List<Appointment> appointments;

    /**
     * Checks if the current Symptom object is equal to the given object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symptom that = (Symptom) o;
        return id == that.id;
    }

    /**
     * Generates a hash code for the Symptom object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
