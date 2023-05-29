package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Appointment;
import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT a FROM Appointment a WHERE LOWER(a.user.name) LIKE %:search%")
    Page<Appointment> findAllAndUserNameContainsIgnoreCase(@Param("search")String search, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId")
    Page<Appointment> findByUser(@Param("userId")int userId, Pageable pageable);

    @Query("SELECT a FROM Appointment a WHERE a.user.id = :userId AND LOWER(a.doctor.user.name) LIKE %:search%")
    Page<Appointment> findByUserAndDoctorNameContainsIgnoreCase(@Param("userId")int userId, @Param("search") String search, Pageable pageable);

    @Query("SELECT a FROM Appointment a JOIN a.doctor d WHERE d.id = :doctorId")
    Page<Appointment> findByDoctor(@Param("doctorId")int doctorId, Pageable pageable);

    @Query("SELECT a FROM Appointment a JOIN a.doctor d WHERE d.id = :doctorId AND LOWER(a.user.name) LIKE %:search%")
    Page<Appointment> findByDoctorAndUserNameContainsIgnoreCase(@Param("doctorId") int doctorId, @Param("search") String search,Pageable pageable);


}
