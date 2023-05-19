package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Modifying
    @Query("DELETE FROM Appointment a where a.id=:id")
    void deleteById(@Param("id") int id);

    List<Appointment> findByUser(User user);

    List<Appointment> findByDoctor(Doctor doctor);
}
