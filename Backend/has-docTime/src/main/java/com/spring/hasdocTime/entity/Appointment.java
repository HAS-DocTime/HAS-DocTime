package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "appointment")
//@JsonIdentityInfo(
//        scope = Appointment.class,
//        generator = ObjectIdGenerators.PropertyGenerator.class,
//        property = "id"
//)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"doctor", "admin", "appointments", "appointmentData", "symptoms"})
    private User user;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"appointments", "user", "availableTimeSlots", "bookedTimeSlots", "postAppointmentData"})
    private Doctor doctor;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "booked_time_slot_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"appointment", "department", "availableDoctors", "bookedDoctors", "appointmentData"})
    private TimeSlot timeSlotForAppointment;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinTable(
            name="appointment_symptom",
            joinColumns = @JoinColumn(name="appointment_id"),
            inverseJoinColumns = @JoinColumn(name="symptom_id")
    )
    @JsonIgnoreProperties({"appointments", "users", "departments"})
    private List<Symptom> symptoms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
