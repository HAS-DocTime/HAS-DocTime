package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.PostAppointmentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostAppointmentDataRepository extends JpaRepository<PostAppointmentData, Integer> {

    @Modifying
    @Query("delete from PostAppointmentData p where p.id = ?1")
    void deleteById(int id);

    @Query("select p from PostAppointmentData p WHERE p.user.email = :userEmail")
    List<PostAppointmentData> findByUserEmail(@Param("userEmail") String email);
}
