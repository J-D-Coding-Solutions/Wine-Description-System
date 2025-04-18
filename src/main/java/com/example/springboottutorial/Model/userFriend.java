package com.example.springboottutorial.Model;

import jakarta.persistence.*;


@Entity
@Table(
        name = "user_friendships",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user", "friend"})
)
public class userFriend {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private users user; // always the lower ID

    @ManyToOne
    private users friend; // always the higher ID

}
