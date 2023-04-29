package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
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
    private Time startTime;

    @Column(name = "end_Time")
    private Time endTime;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"timeSlots", "doctors"})
    private Department department;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "available_time_slot",
            joinColumns = @JoinColumn(name = "time_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    @JsonIgnoreProperties({"availableTimeSlots", "department","bookedTimeSlots", "appointments", "postAppointmentData"})
    private List<Doctor> availableDoctors;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "booked_time_slot",
            joinColumns = @JoinColumn(name = "time_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    @JsonIgnoreProperties({"availableTimeSlots", "department","bookedTimeSlots", "appointments", "postAppointmentData"})
    private List<Doctor> bookedDoctors;

    @OneToOne(mappedBy = "timeSlotForAppointmentData", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"timeSlotForAppointmentData", "user", "doctor"})
    private PostAppointmentData appointmentData;

    @OneToOne(mappedBy = "timeSlotForAppointment", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"timeSlotForAppointment", "symptoms", "user", "doctor"})
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
