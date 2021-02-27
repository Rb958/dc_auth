package com.sabc.digitalchampions.repository;

import com.sabc.digitalchampions.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
//    User findByUsername(String username);
    User findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    @Query("select u from User u where u.firstname like %?1% or u.lastname like %?2%")
    Page<User> findAll(String fistName, String lastName, Pageable pageable);

    boolean existsByCode(String code);

    User findByCode(String code);

    void deleteByCode(String code);

    boolean existsByRole(String role);

    User findByPhone(String phone);
}
