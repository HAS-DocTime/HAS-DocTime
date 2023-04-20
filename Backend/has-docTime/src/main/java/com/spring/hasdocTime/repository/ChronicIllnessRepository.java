package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.ChronicIllness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChronicIllnessRepository extends JpaRepository<ChronicIllness, Integer> {

    @Modifying
    @Query("DELETE FROM ChronicIllness p where p.id = :id")
    void deleteById(@Param("id") int id);
}
