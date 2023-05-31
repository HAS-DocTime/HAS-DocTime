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

/**
 * Repository interface for accessing and manipulating Appointment entities.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    /**
     * Deletes an appointment by ID.
     *
     * @param id the ID of the appointment to be deleted
     */
    @Modifying
    @Query("DELETE FROM Appointment a where a.id=:id")
    void deleteById(@Param("id") int id);

    /**
     * Retrieves a list of appointments associated with the specified user.
     *
     * @param user the user entity
     * @return a list of appointments associated with the user
     */
    List<Appointment> findByUser(User user);

    /**
     * Retrieves a list of appointments associated with the specified doctor.
     *
     * @param doctor the doctor entity
     * @return a list of appointments associated with the doctor
     */
    List<Appointment> findByDoctor(Doctor doctor);
}
