package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Modifying
    @Query("DELETE FROM Appointment a where a.id=:id")
    void deleteById(@Param("id") int id);
}
