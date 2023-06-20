package com.spring.hasdoctime.repository;

import com.spring.hasdoctime.entity.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT a FROM Appointment a WHERE LOWER(a.user.name) LIKE %:search% ")
    Page<Appointment> findAllAndUserNameContainsIgnoreCase(@Param("search")String search, Pageable pageable);

    /**
     * Retrieves a list of appointments associated with the specified user.
     *
     * @param user the user entity
     * @return a list of appointments associated with the user
     */
    Page<Appointment> findByUserId(@Param("userId")int userId, Pageable pageable);

    List<Appointment> findListByUserId(int userId);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND LOWER(a.doctor.user.name) LIKE %:search%")
    Page<Appointment> findByUserIdAndDoctorNameContainsIgnoreCase(@Param("userId")int userId, @Param("search") String search, Pageable pageable);

    /**
     * Retrieves a list of appointments associated with the specified doctor.
     *
     * @param doctor the doctor entity
     * @return a list of appointments associated with the doctor
     */
    Page<Appointment> findByDoctorId(@Param("doctorId")int doctorId, Pageable pageable);

    Page<Appointment> findByDoctorIdAndUserNameContainsIgnoreCase(@Param("doctorId") int doctorId, @Param("search") String search,Pageable pageable);


}
