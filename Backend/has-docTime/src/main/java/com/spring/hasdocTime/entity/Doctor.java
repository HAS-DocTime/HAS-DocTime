/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Objects;

/**
 * The Doctor class represents a doctor in the system.
 * It contains information about the doctor's ID, associated user, qualification,
 * department, number of cases solved, availability, available time slots,
 * booked time slots, appointments, and post-appointment data.
 */
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
    @NotBlank(message = "Please enter your Qualification")
    @Pattern(regexp = "[a-zA-Z0-9,.-]+( [a-zA-Z0-9,.-]+)*", message = "Invalid input. Please enter a valid string with alphanumeric characters, commas, periods, and dashes, allowing spaces in between.")
    private String qualification;
   
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"doctors", "symptoms", "timeSlots"}, allowSetters = true)
    private Department department;
    
    @Column(name="cases_solved")
    @Min(value = 0, message = "Please enter valid number of cases solved")
    @Max(value = 99999, message = "Please enter valid number of cases solved")
    private int casesSolved;

    
    @Column(name="is_available")
    @NotNull(message = "Please enter boolean value")
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


    /**
     * Checks if the current Doctor object is equal to the given object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return id == doctor.id;
    }


    /**
     * Generates a hash code for the Doctor object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
