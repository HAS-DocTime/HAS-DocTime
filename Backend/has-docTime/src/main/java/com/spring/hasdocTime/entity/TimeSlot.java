package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private Timestamp startTime;

    @Column(name = "end_Time")
    private Timestamp endTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
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

    @OneToOne(mappedBy = "timeSlotForAppointmentData", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"timeSlotForAppointmentData", "user", "doctor"}, allowSetters = true)
    private PostAppointmentData appointmentData;

    @OneToOne(mappedBy = "timeSlotForAppointment", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = {"timeSlotForAppointment", "symptoms", "user", "doctor"}, allowSetters = true)
    private Appointment appointment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot that = (TimeSlot) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
