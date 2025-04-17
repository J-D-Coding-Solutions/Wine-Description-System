package com.example.springboottutorial.Repository;

import com.example.springboottutorial.Model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WineRequestRepository extends JpaRepository <WineRequests,Long> {
}
