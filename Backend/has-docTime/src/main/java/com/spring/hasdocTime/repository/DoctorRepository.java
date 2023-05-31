/**
 * Repository interface for accessing and manipulating Doctor entities.
 */
package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    /**
     * Deletes a doctor by ID.
     *
     * @param id the ID of the doctor to be deleted
     */
    @Modifying
    @Query("DELETE FROM Doctor d WHERE d.id = :id")
    void deleteById(@Param("id") int id);

    /**
     * Retrieves a list of doctors belonging to a specific department.
     *
     * @param id the ID of the department
     * @return a list of doctors belonging to the specified department
     */
    @Query("FROM Doctor d WHERE d.department.id = :id")
    List<Doctor> findDoctorsByDepartmentId(@Param("id") int id);
}
