package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.PostAppointmentData;
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
    List<PostAppointmentData> findByUserEmail(@Param("userEmail") String email);

    @Query("SELECT p.disease AS disease, COUNT(p) AS caseCount FROM PostAppointmentData p WHERE LOWER(p.symptoms) LIKE %:symptom% GROUP BY p.disease")
    List<Map<String, Integer>> findDiseasesGroupedBySymptom(@Param("symptom") String symptom);

    @Query("SELECT p from PostAppointmentData p where LOWER(p.symptoms) LIKE %:symptom%")
    List<PostAppointmentData> findPostAppointmentDataGroupedBySymptom(@Param("symptom") String symptom);
}
