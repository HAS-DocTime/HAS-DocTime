package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Doctor;
import com.spring.hasdocTime.entity.PostAppointmentData;
import com.spring.hasdocTime.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PostAppointmentDataRepository extends JpaRepository<PostAppointmentData, Integer> {

    @Modifying
    @Query("delete from PostAppointmentData p where p.id = ?1")
    void deleteById(int id);

    @Query("select p from PostAppointmentData p WHERE p.user.email = :userEmail")
    Page<PostAppointmentData> findByUserEmail(@Param("userEmail") String email, Pageable pageable);

    @Query("select p from PostAppointmentData p WHERE p.user.email = :userEmail AND LOWER(p.disease) LIKE %:search")
    Page<PostAppointmentData> findByUserEmailAndDiseaseContainsIgnoreCase(@Param("userEmail") String email, @Param("search") String search, Pageable pageable);

    @Query("SELECT p.disease AS disease, COUNT(p) AS caseCount FROM PostAppointmentData p WHERE LOWER(p.symptoms) LIKE %:symptom% GROUP BY p.disease")
    List<Map<String, Integer>> findDiseaseListGroupedBySymptom(@Param("symptom") String symptom);

    @Query("SELECT p from PostAppointmentData p where LOWER(p.symptoms) LIKE %:symptom%")
    Page<PostAppointmentData> findPostAppointmentDataGroupedBySymptom(@Param("symptom") String symptom, Pageable pageable);

    @Query("SELECT p from PostAppointmentData p where LOWER(p.symptoms) LIKE %:symptom% AND LOWER(p.user.name) LIKE %:search%")
    Page<PostAppointmentData> findPostAppointmentDataGroupedBySymptomAndUserNameContainsIgnoreCase(@Param("symptom")String symptom,@Param("search") String search, Pageable pageable);

    @Query("SELECT p from PostAppointmentData p where LOWER(p.user.name) LIKE %:search%")
    Page<PostAppointmentData> findAllAndUserNameContainsIgnoreCase(@Param("search")String search, Pageable pageable);

    @Query("SELECT p from PostAppointmentData p WHERE p.doctor.id = :doctorId AND LOWER(p.user.name) LIKE %:search%")
    Page<PostAppointmentData> findByDoctorAndUserNameContainsIgnoreCase(@Param("doctorId") int doctorId, @Param("search")String search, Pageable pageable);

    @Query("SELECT p from PostAppointmentData p WHERE p.doctor.id = :doctorId")
    Page<PostAppointmentData> findByDoctor(@Param("doctorId") int doctorId, Pageable pageable);

    @Query("SELECT p from PostAppointmentData p JOIN p.user u WHERE u.id = :userId AND LOWER(p.doctor.user.name) LIKE %:search%")
    Page<PostAppointmentData> findByUserAndDoctorNameContainsIgnoreCase(@Param("userId") int userId, @Param("search")String search, Pageable pageable);

    @Query("SELECT p from PostAppointmentData p WHERE p.user.id = :userId")
    Page<PostAppointmentData> findByUser(@Param("userId") int userId, Pageable pageable);
}
