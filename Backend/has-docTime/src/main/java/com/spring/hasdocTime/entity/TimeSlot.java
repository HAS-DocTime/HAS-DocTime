package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.sql.Time;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * The TimeSlot class represents a time slot in the system.
 * It contains information about the time slot's ID, start time, end time,
 * associated department, available doctors, booked doctors, appointment data,
 * and appointment.
 */
@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "start_time")
    @Future(message = "Timestamp must be in the future")
    private Time startTime;

    @Column(name = "end_Time")
    @Future(message = "Timestamp must be in the future")
    private Time endTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"timeSlots", "doctors"}, allowSetters = true)
    private Department department;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "available_time_slot",
            joinColumns = @JoinColumn(name = "time_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    @JsonIgnoreProperties(value = {"availableTimeSlots", "department","bookedTimeSlots", "appointments", "postAppointmentData"}, allowSetters = true)
    private List<Doctor> availableDoctors;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "booked_time_slot",
            joinColumns = @JoinColumn(name = "time_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    @JsonIgnoreProperties(value = {"availableTimeSlots", "department","bookedTimeSlots", "appointments", "postAppointmentData"}, allowSetters = true)
    private List<Doctor> bookedDoctors;

    @OneToOne(mappedBy = "timeSlotForAppointmentData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"timeSlotForAppointmentData", "user", "doctor"}, allowSetters = true)
    private PostAppointmentData appointmentData;

    @OneToOne(mappedBy = "timeSlotForAppointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"timeSlotForAppointment", "symptoms", "user", "doctor"}, allowSetters = true)
    private Appointment appointment;

    /**
     * Checks if the current TimeSlot object is equal to the given object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot that = (TimeSlot) o;
        return id == that.id;
    }

    /**
     * Generates a hash code for the TimeSlot object.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
