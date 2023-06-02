/**
 * Repository interface for accessing and manipulating PostAppointmentData entities.
 */
package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.PostAppointmentData;
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
    @Query("SELECT p FROM PostAppointmentData p WHERE p.user.email = :userEmail")
    List<PostAppointmentData> findByUserEmail(@Param("userEmail") String userEmail);

    /**
     * Retrieves a list of diseases and their corresponding case counts grouped by a specific symptom.
     *
     * @param symptom the symptom to group the diseases by
     * @return the list of diseases and their case counts grouped by the symptom
     */
    @Query("SELECT p.disease AS disease, COUNT(p) AS caseCount FROM PostAppointmentData p WHERE LOWER(p.symptoms) LIKE %:symptom% GROUP BY p.disease")
    List<Map<String, Integer>> findDiseasesGroupedBySymptom(@Param("symptom") String symptom);

    /**
     * Retrieves a list of PostAppointmentData records associated with a specific symptom.
     *
     * @param symptom the symptom to search for
     * @return the list of PostAppointmentData records associated with the symptom
     */
    @Query("SELECT p FROM PostAppointmentData p WHERE LOWER(p.symptoms) LIKE %:symptom%")
    List<PostAppointmentData> findPostAppointmentDataGroupedBySymptom(@Param("symptom") String symptom);
}
