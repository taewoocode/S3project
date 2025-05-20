package com.example.s3test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.s3test.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
