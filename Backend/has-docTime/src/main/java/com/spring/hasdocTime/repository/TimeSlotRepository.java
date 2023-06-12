/**
 * Repository interface for accessing and manipulating TimeSlot entities.
 */
package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {

    /**
     * Deletes a TimeSlot record by ID.
     *
     * @param id the ID of the TimeSlot record to be deleted
     */
    @Modifying
    @Query("DELETE FROM TimeSlot t WHERE t.id = :id")
    void deleteById(@Param("id") int id);

    /**
     * Deletes TimeSlot records associated with a Department by Department ID.
     *
     * @param departmentId the ID of the Department
     */
    @Modifying
    @Query("DELETE FROM TimeSlot t WHERE t.department.id = :departmentId")
    void deleteByDepartment(@Param("departmentId") int departmentId);

    @Query("SELECT t from TimeSlot t where startTime = :startTime and endTime = :endTime and department.id = :departmentId")
    TimeSlot checkIfTimeSlotExists(@Param("startTime") Timestamp startTime, @Param("endTime") Timestamp endTime, @Param("departmentId") int departmentId);
}
