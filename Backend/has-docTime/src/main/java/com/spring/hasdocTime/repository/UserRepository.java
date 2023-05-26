package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u where LOWER(u.name) LIKE %:search%")
    Page<User> findAllAndNameContainsIgnoreCase(@Param("search") String search, Pageable pageable);

    @Query("FROM User u where u.role=PATIENT")
    Page<User> getPatients(Pageable pageable);

    @Query("FROM User u where u.role=PATIENT and LOWER(u.name) LIKE %:search%")
    Page<User> getPatientsAndNameContainsIgnoreCase(@Param("search") String search,Pageable pageable);

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    void deleteById(@PathVariable int id);

    @Query("SELECT u FROM User u JOIN u.patientChronicIllness pc where pc.chronicIllness.id = :id AND LOWER(u.name) LIKE %:search%")
    Page<User> findByChronicIllnessIdAndNameContainsIgnoreCase(@Param("id")int id, @Param("search") String search, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.patientChronicIllness pc where pc.chronicIllness.id = :id ")
//    @Query("SELECT u FROM User u JOIN u.patientChronicIllness pc JOIN pc.chronicIllness c WHERE c.id = :id ")
    Page<User> findByChronicIllnessId(@Param("id") int id, Pageable pageable);

}
