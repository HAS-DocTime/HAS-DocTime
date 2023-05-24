/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author arpit
 */

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
    
    @Modifying
    @Query("DELETE FROM Doctor d where d.id = :id")
    void deleteById(@Param("id") int id);


    @Query("FROM Doctor d where d.department.id =:id")
    List<Doctor> findDoctorsByDepartmentId(@Param("id") int id);
}
