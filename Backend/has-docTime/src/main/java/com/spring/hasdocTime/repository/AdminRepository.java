package com.spring.hasdocTime.repository;

import com.spring.hasdocTime.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

//    @Query("DELETE FROM admin WHERE id = :id AND user_id = :userId")
//    void deleteAdminByIdAndUserId(int id, int userId);

    @Modifying
    @Query("DELETE FROM Admin a WHERE a.id = :id")
    void deleteById(@Param("id") int id);
}
