package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.ChronicIllness;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChronicIllnessRepository extends JpaRepository<ChronicIllness, Integer> {
}
