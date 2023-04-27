package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "symptom")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@JsonIdentityInfo(
//        scope = Symptom.class,
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id")
public class Symptom {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "patient_symptom",
            joinColumns = @JoinColumn(name = "symptom_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    @JsonIgnoreProperties("symptoms")
    private List<User> users;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
    @JoinTable(
            name = "department_symptom",
            joinColumns = @JoinColumn(name = "symptom_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    @JsonIgnoreProperties("symptoms")
    private List<Department> departments;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "appointment_symptom",
            joinColumns = @JoinColumn(name = "symptom_id"),
            inverseJoinColumns = @JoinColumn(name = "appointment_id"))
    @JsonIgnoreProperties("symptoms")
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
