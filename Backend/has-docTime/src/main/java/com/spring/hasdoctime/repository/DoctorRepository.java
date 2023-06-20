/**
 * Repository interface for accessing and manipulating Doctor entities.
 */
package com.spring.hasdoctime.repository;

import com.spring.hasdoctime.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
    @Query("FROM Doctor d where d.department.id =:id")
    Page<Doctor> findDoctorsByDepartmentId(@Param("id") int id, Pageable pageable);

    @Query("FROM Doctor d WHERE LOWER(d.user.name) LIKE %:search%")
    Page<Doctor> findAllAndDoctorNameContainsIgnoreCase(@Param("search") String search, Pageable pageable);

    @Query("FROM Doctor d where d.department.id =:id and LOWER(d.user.name) LIKE %:search%")
    Page<Doctor> findDoctorsByDepartmentIdAndDoctorNameContainsIgnoreCase(@Param("search") String search, Pageable pageable);
}
