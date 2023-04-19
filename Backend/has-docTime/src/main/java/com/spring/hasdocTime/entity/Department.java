/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

/**
 *
 * @author arpit
 */

@Entity
@Table(name="department")
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
@JsonIdentityInfo(
        scope = Department.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Department {
    
    @Column(name="id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name="name")
    private String name;
    
    @Column(name="building")
    private String building;
    
    @Column(name="time_duration")
    private int timeDuration;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private Set<Doctor> doctors;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "department_symptom",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name="symptom_id")
    )
    private Set<Symptom> symptoms;
    
    @OneToMany(mappedBy = "department", cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    private Set<TimeSlot> timeSlots;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
