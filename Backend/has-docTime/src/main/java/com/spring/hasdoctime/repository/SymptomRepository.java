/**
 * Repository interface for accessing and manipulating Symptom entities.
 */
package com.spring.hasdoctime.repository;

import com.spring.hasdoctime.entity.Symptom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Integer> {

    /**
     * Deletes a Symptom record by ID.
     *
     * @param id the ID of the Symptom record to be deleted
     */
    @Modifying
    @Query("DELETE FROM Symptom s WHERE s.id = :id")
    void deleteById(@Param("id") int id);

    @Query("SELECT s FROM Symptom s WHERE LOWER(s.name) LIKE %:search%")
    Page<Symptom> findAllAndNameContainsIgnoreCase(@Param("search")String search, Pageable pageable);
}
