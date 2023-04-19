package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "appointment")
@JsonIdentityInfo(
        scope = Appointment.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
//    @JsonIgnore
    private User user;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
//    @JsonIgnore
    private Doctor doctor;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "booked_time_slot_id", referencedColumnName = "id")
//    @JsonIgnore
    private TimeSlot timeSlotForAppointment;

    @ManyToMany(mappedBy = "appointments", cascade = {CascadeType.REFRESH, CascadeType.PERSIST})
    private Set<Symptom> symptoms;

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
