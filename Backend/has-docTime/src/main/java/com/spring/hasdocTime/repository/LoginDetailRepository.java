package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.LoginDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginDetailRepository extends JpaRepository<LoginDetail, Integer> {

    Optional<LoginDetail> findByUsername(String username);

}
