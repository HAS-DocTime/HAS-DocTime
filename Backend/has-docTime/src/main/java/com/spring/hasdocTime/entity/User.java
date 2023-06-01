package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.spring.hasdocTime.utills.BloodGroup;
import com.spring.hasdocTime.utills.Gender;
import com.spring.hasdocTime.utills.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * The User class represents a user in the system.
 * It implements the UserDetails interface for Spring Security integration.
 * It contains information about the user's ID, name, date of birth, age,
 * gender, blood group, contact number, height, weight, email, password,
 * role, associated doctor, associated admin, patient chronic illnesses,
 * appointments, appointment data, and symptoms.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table(name="user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Enter your name")
    @Pattern(regexp = "^[a-zA-Z]+([.\\s]?[a-zA-Z]+)*$", message = "Invalid name format. Only a dot or one space is allowed in between.")
    private String name;

    @Column(name = "dob")
    @NotNull(message = "Invalid date format. Please provide a date in yyyy-MM-dd format.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @Column(name="age")
    private int age;

    @Column(name = "gender")
    private Gender gender;

    @Column(name="blood_group")
    private BloodGroup bloodGroup;

    @Column(name="profile_picture")
    private String imageUrl;

    @Column(name="contact")
    @NotBlank(message = "Please enter a contact number")
    @Pattern(regexp = "\\+[0-9]{1,3}-[0-9]{10}", message = "Please enter a valid contact number")
    private String contact;

    @Column(name="height")
    @DecimalMin(value = "1.0", inclusive = true, message = "Height should not be less than 1ft")
    @DecimalMax(value = "10.0", inclusive = true, message = "Height should not be more than 10ft")
    private float height;

    @Column(name="weight")
    @DecimalMin(value = "1.0", inclusive = true, message = "Weight should not be less than 1kg")
    @DecimalMax(value = "300.0", inclusive = true, message = "Weight should not be more than 300kg")
    private float weight;

    @Column(name="email")
    @NotBlank(message = "Please enter your email")
    @Email(message = "Please provide a valid email address")
    private String email;

    @Column(name="password")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#$%^&+=_\\-\\*/])(?=\\S+$).{8,}$",
            message = "Invalid password. Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, one special character, and no whitespace.")
    private String password;

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private Doctor doctor;

    @JsonIgnoreProperties(value = "user", allowSetters = true)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Admin admin;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<PatientChronicIllness> patientChronicIllness;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    private List<PostAppointmentData> appointmentData;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "patient_symptom",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "symptom_id"))
    @JsonIgnoreProperties(value = {"users", "departments", "appointments"}, allowSetters = true)
    private List<Symptom> symptoms;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}