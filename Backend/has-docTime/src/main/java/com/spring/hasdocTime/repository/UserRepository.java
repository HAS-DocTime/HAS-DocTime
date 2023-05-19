package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.User;
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

    @Query("FROM User u where u.role=PATIENT")
    List<User> getPatients();


    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    void deleteById(@PathVariable int id);

}
