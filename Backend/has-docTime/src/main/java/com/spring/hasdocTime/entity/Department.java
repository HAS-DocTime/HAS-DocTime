/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Objects;

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
    @NotBlank(message = "Please enter name")
    @Pattern(regexp = "^[a-zA-Z]+([. _-]?[a-zA-Z]+)*$", message = "Please enter Valid Name")
    private String name;
    
    @Column(name="building")
    @NotBlank(message = "Please enter Building Number")
    @Pattern(regexp = "^[a-zA-Z0-9]+([. _-]?[a-zA-Z0-9]+)*$", message = "Please enter valid Building Id")
    private String building;
    
    @Column(name="time_duration")
    @NotBlank(message = "Please enter Time Duration")
    @Size(min=0, max=60, message = "Please enter valid minutes")
    private int timeDuration;
    
    @Column(name="description")
    private String description;
    
    @Column(name="department_image")
    private String departmentImage;
          
    @OneToMany(mappedBy = "department", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIgnoreProperties(value = {"user", "department", "appointments", "postAppointmentData"}, allowSetters = true)
    private List<Doctor> doctors;

    @ManyToMany()
    @JoinTable(name = "department_symptom",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name="symptom_id")
    )
    @JsonIgnoreProperties(value = {"departments", "users", "appointments"}, allowSetters = true)
    private List<Symptom> symptoms;
    
    @OneToMany(mappedBy = "department", cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
    @JsonIgnoreProperties(value = {"department", "availableDoctors", "bookedDoctors", "appointmentData", "appointment"}, allowSetters = true)
    private List<TimeSlot> timeSlots;


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
