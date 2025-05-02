package com.example.springboottutorial.Repository;

import com.example.springboottutorial.Model.favoriteWines;
import com.example.springboottutorial.Model.users;
import com.example.springboottutorial.Model.wines;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteWineRepository extends JpaRepository<favoriteWines, Long> {
    boolean existsByUserAndWine(users currentUser, wines wine);
    List<favoriteWines> findAllByUser(users currentUser);
    void deleteByWineAndUser(wines wine, users currentUser);
}
