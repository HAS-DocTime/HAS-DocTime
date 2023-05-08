package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Department;
import com.spring.hasdocTime.entity.TimeSlot;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Integer> {

    @Modifying
    @Query("DELETE FROM TimeSlot t where t.id= :id")
    void deleteById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM TimeSlot t where t.department.id=:departmentId")
    void deleteByDepartment(@Param("departmentId") int departmentId);
}
