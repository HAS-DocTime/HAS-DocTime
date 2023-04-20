package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Integer> {

    @Modifying
    @Query("DELETE FROM Symptom s WHERE s.id = :id")
    void deleteById(@Param("id") int id);
}
