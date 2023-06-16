/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

/**
 * The Department class represents a department in the system.
 * It contains information about the department's ID, name, building, time duration, description,
 * department image, associated doctors, symptoms, and time slots.
 */

@Entity
@Table(name="department")
@NoArgsConstructor
@Data
@AllArgsConstructor
@ToString
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
    @NotNull(message = "Time duration must not be null")
    @Min(value = 0, message = "Minutes entered should be more than 0")
    @Max(value = 60, message = "Minutes entered should not be more than 60")
    private int timeDuration;
    
    @Column(name="description")
    private String description;
    
    @Column(name="department_image")
    private String departmentImage;
          
    @OneToMany(mappedBy = "department", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    @JsonIgnoreProperties(value = {"user", "department", "appointments", "postAppointmentData"}, allowSetters = true)
    private List<Doctor> doctors;

    @ManyToMany()
    @JoinTable(name = "department_symptom",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name="symptom_id")
    )
    @JsonIgnoreProperties(value = {"departments", "users", "appointments"}, allowSetters = true)
    private Set<Symptom> symptoms = new HashSet<>();
    
    @OneToMany(mappedBy = "department", cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnoreProperties(value = {"department", "availableDoctors", "bookedDoctors", "appointmentData", "appointment"}, allowSetters = true)
    private List<TimeSlot> timeSlots = new ArrayList<>();


    /**
     * Checks if the current Department object is equal to the given object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id == that.id;
    }


    /**
     * Generates a hash code value for the Department object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
