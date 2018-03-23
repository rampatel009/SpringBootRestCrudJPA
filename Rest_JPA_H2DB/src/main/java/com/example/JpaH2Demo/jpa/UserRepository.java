package com.example.JpaH2Demo.jpa;

import com.example.JpaH2Demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    // Custom query and return a stream
    @Query("select std from User std where std.passportNumber = :passportNumber")
    Stream<User> findByPassportNumberReturnStream(@Param("passportNumber") String passportNumber);
}
