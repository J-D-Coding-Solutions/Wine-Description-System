package com.example.springboottutorial.Repository;

import com.example.springboottutorial.Model.users;
import com.example.springboottutorial.Model.friendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRequestRepository extends JpaRepository<friendRequest, Long> {
    boolean existsBySenderAndFriend(users sender, users friend);

    List<friendRequest> findAllByfriend(users friend);
}
