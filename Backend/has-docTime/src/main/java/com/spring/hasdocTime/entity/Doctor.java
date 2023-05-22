/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "doctor")
public class Doctor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    
    
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties(value = {"doctor", "admin", "appointments", "appointmentData", "symptoms", "patientChronicIllness"}, allowSetters = true)
    private User user;
    
    @Column(name="qualification")
    private String qualification;
   
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"doctors", "symptoms", "timeSlots"}, allowSetters = true)
    private Department department;
    
    @Column(name="cases_solved")
    private int casesSolved;
    
    @Column(name="is_available")
    private boolean isAvailable;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "availableDoctors")
    @JsonIgnoreProperties(value = {"department", "availableDoctors", "bookedDoctors", "appointmentData", "appointment"}, allowSetters = true)
    private List<TimeSlot> availableTimeSlots;
    
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "bookedDoctors")
    @JsonIgnoreProperties(value = {"department", "availableDoctors", "bookedDoctors", "appointmentData", "appointment"}, allowSetters = true)
    private List<TimeSlot> bookedTimeSlots;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    @JsonIgnoreProperties(value = "doctor", allowSetters = true)
    private List<Appointment> appointments;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor")
    @JsonIgnoreProperties(value = "doctor", allowSetters = true)
    private List<PostAppointmentData> postAppointmentData;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
