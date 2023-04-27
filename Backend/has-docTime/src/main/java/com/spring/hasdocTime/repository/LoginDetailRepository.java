package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.LoginDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginDetailRepository extends JpaRepository<LoginDetail, Integer> {

    Optional<LoginDetail> findByUsername(String username);

}
