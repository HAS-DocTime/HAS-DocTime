package com.spring.hasdocTime.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(name = "admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIdentityInfo(
        scope = Admin.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Admin {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return id == admin.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
