/**
 * Repository interface for accessing and manipulating User entities.
 */
package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.User;
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

    /**
     * Retrieves all User records with the role PATIENT.
     *
     * @return a list of Patient User records
     */
    @Query("FROM User u WHERE u.role = 'PATIENT'")
    List<User> getPatients();

    /**
     * Deletes a User record by ID.
     *
     * @param id the ID of the User record to be deleted
     */
    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    void deleteById(@Param("id") int id);
}
