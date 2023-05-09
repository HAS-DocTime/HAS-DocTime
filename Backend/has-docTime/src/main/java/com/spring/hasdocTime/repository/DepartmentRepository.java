/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author arpit
 */


@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer>{
    
    @Modifying
    @Query("DELETE FROM Department d where d.id= :id")
    void deleteById(@Param("id") int id);

}
