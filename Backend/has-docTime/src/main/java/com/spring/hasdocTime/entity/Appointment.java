package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

import java.util.Objects;


/**
 * The Appointment class represents an appointment in the system.
 * It contains information about the appointment's ID, description, associated User (patient),
 * associated Doctor, time slot, and list of symptoms.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"doctor", "admin", "appointments", "appointmentData", "symptoms"}, allowSetters = true)
    private User user;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"appointments", "availableTimeSlots", "bookedTimeSlots", "postAppointmentData"}, allowSetters = true)
    private Doctor doctor;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_time_slot_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"appointment", "department", "availableDoctors", "bookedDoctors", "appointmentData"}, allowSetters = true)
    private TimeSlot timeSlotForAppointment;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name="appointment_symptom",
            joinColumns = @JoinColumn(name="appointment_id"),
            inverseJoinColumns = @JoinColumn(name="symptom_id")
    )
    @JsonIgnoreProperties(value = {"appointments", "users", "departments"}, allowSetters = true)
    private List<Symptom> symptoms;


    /**
     * Checks if the current Appointment object is equal to the given object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id;
    }


    /**
     * Generates a hash code value for the Appointment object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
