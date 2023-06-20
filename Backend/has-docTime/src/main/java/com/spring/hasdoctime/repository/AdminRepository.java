package com.spring.hasdoctime.repository;

import com.spring.hasdoctime.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing and manipulating Admin entities.
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    /**
     * Deletes an admin by ID.
     *
     * @param id the ID of the admin to be deleted
     */
    @Modifying
    @Query("DELETE FROM Admin a WHERE a.id = :id")
    void deleteById(@Param("id") int id);
}
