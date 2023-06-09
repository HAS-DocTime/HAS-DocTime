/**
 * Repository interface for accessing and manipulating User entities.
 */
package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieves a User record by email.
     *
     * @param email the email of the User
     * @return an Optional containing the User record, or an empty Optional if not found
     */
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u where LOWER(u.name) LIKE %:search%")
    Page<User> findAllAndNameContainsIgnoreCase(@Param("search") String search, Pageable pageable);

    /**
     * Retrieves all User records with the role PATIENT.
     *
     * @return a list of Patient User records
     */
    @Query("FROM User u where u.role=PATIENT")
    Page<User> getPatients(Pageable pageable);

    @Query("FROM User u where u.role=PATIENT and LOWER(u.name) LIKE %:search%")
    Page<User> getPatientsAndNameContainsIgnoreCase(@Param("search") String search,Pageable pageable);

    /**
     * Deletes a User record by ID.
     *
     * @param id the ID of the User record to be deleted
     */
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    void deleteById(@Param("id") int id);

    @Query("SELECT u FROM User u JOIN u.patientChronicIllness pc where pc.chronicIllness.id = :id AND LOWER(u.name) LIKE %:search%")
    Page<User> findByChronicIllnessIdAndNameContainsIgnoreCase(@Param("id")int id, @Param("search") String search, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.patientChronicIllness pc where pc.chronicIllness.id = :id ")
    Page<User> findByChronicIllnessId(@Param("id") int id, Pageable pageable);

}
