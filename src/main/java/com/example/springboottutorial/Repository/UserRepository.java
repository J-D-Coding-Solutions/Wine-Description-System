package com.example.springboottutorial.Repository;

import com.example.springboottutorial.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<users, Long> {
    Optional<users> findByUsername(String username);

    List<users> findByIdNot(Long id);

    List<users> id(Long id);
}
