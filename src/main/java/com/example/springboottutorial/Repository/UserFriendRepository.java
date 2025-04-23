package com.example.springboottutorial.Repository;

import com.example.springboottutorial.Model.userFriend;
import com.example.springboottutorial.Model.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserFriendRepository extends JpaRepository<userFriend, Long> {

    @Query("SELECT uf FROM userFriend uf WHERE uf.user.user_id = :userId OR uf.friend.user_id = :userId")
    List<userFriend> findAllByUserIdOrFriendId(@Param("userId") Long userId);

    boolean existsByUserAndFriend(users currentUser, users friendUser);
}
