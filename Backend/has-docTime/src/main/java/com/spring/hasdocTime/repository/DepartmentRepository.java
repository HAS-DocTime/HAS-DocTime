/**
 * Repository interface for accessing and manipulating Department entities.
 */
package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    /**
     * Deletes a department by ID.
     *
     * @param id the ID of the department to be deleted
     */
    @Modifying
    @Query("DELETE FROM Department d WHERE d.id = :id")
    void deleteById(@Param("id") int id);
}
