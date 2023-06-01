package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Objects;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_appointment_data")
public class PostAppointmentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "disease")
    @Pattern(regexp = "[a-zA-Z,.-]+( [a-zA-Z,.-]+)*", message = "Invalid input. Please enter a valid string with alphanumeric characters, commas, periods, and dashes, allowing spaces in between.")
    private String disease;

    @Column(name = "medicine")
    @Pattern(regexp = "[a-zA-Z0-9:,.-]+( [a-zA-Z0-9:,.-]+)*", message = "Invalid input. Please enter a valid string with alphanumeric characters, commas, periods, colons, and dashes allowing spaces in between.")
    private String medicine;

    @Column(name="symptoms")
    @NotEmpty
    @Pattern(regexp = "[a-zA-Z,.-]+( [a-zA-Z,.-]+)*", message = "Invalid input. Please enter a valid string with alphabetic characters, commas, periods, and dashes allowing spaces in between.")
    private String symptoms;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonIgnoreProperties(value={"appointmentData", "admin", "appointments", "doctor"}, allowSetters = true)
    private User user;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"postAppointmentData", "availableTimeSlots", "bookedTimeSlots", "appointments"}, allowSetters = true)
    private Doctor doctor;

    @OneToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "booked_time_slot_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"appointmentData", "bookedDoctors", "availableDoctors", "department", "appointment"}, allowSetters = true)
    private TimeSlot timeSlotForAppointmentData;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostAppointmentData that = (PostAppointmentData) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
