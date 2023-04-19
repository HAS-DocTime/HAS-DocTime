package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.PostAppointmentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostAppointmentDataRepository extends JpaRepository<PostAppointmentData, Integer> {

    @Modifying
    @Query("delete from PostAppointmentData p where p.id = ?1")
    void deleteById(int id);

}
