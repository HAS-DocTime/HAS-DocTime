/**
 * Repository interface for accessing and manipulating PostAppointmentData entities.
 */
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

@Repository
public interface PostAppointmentDataRepository extends JpaRepository<PostAppointmentData, Integer> {

    /**
     * Deletes a PostAppointmentData record by ID.
     *
     * @param id the ID of the PostAppointmentData record to be deleted
     */
    @Modifying
    @Query("DELETE FROM PostAppointmentData p WHERE p.id = :id")
    void deleteById(@Param("id") int id);

    /**
     * Retrieves a list of PostAppointmentData records associated with a specific user email.
     *
     * @param userEmail the email of the user
     * @return the list of PostAppointmentData records associated with the user email
     */
    @Query("select p from PostAppointmentData p WHERE p.user.email = :userEmail")
    Page<PostAppointmentData> findByUserEmail(@Param("userEmail") String email, Pageable pageable);

    @Query("select p from PostAppointmentData p WHERE p.user.email = :userEmail AND LOWER(p.disease) LIKE %:search")
    Page<PostAppointmentData> findByUserEmailAndDiseaseContainsIgnoreCase(@Param("userEmail") String email, @Param("search") String search, Pageable pageable);

    /**
     * Retrieves a list of diseases and their corresponding case counts grouped by a specific symptom.
     *
     * @param symptom the symptom to group the diseases by
     * @return the list of diseases and their case counts grouped by the symptom
     */
    @Query("SELECT p.disease AS disease, COUNT(p) AS caseCount FROM PostAppointmentData p WHERE LOWER(p.symptoms) LIKE %:symptom% GROUP BY p.disease")
    List<Map<String, Integer>> findDiseaseListGroupedBySymptom(@Param("symptom") String symptom);

    @Query("SELECT p from PostAppointmentData p where LOWER(p.symptoms) LIKE %:symptom%")
    Page<PostAppointmentData> findPostAppointmentDataGroupedBySymptom(@Param("symptom") String symptom, Pageable pageable);

    /**
     * Retrieves a list of PostAppointmentData records associated with a specific symptom.
     *
     * @param symptom the symptom to search for
     * @return the list of PostAppointmentData records associated with the symptom
     */
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
