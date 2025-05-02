package com.example.springboottutorial.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
//@Table(
//        name = "user_friendships",
//        uniqueConstraints = @UniqueConstraint(columnNames = {"user", "friend"})
//)
public class userFriend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private users user; // always the lower ID

    @ManyToOne
    @JoinColumn(name = "friend_user_Id", nullable = false, referencedColumnName = "user_id")
    private users friend; // always the higher ID

    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    public userFriend() {};

    public userFriend(users user, users friend) {
        this.user = user;
        this.friend = friend;
        this.created_at = LocalDateTime.now();
    }

    @PrePersist//this says as soon as this is ran get a timestamp and put it in the created_at collumn
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    public Long getId() {return this.id;}
    public users getUser(){return this.user;}
    public users getFriend(){return this.friend;}
    public LocalDateTime getCreatedAt() { return created_at; }

    public void setUser(users user) { this.user = user; }
    public void setFriend(users friend) { this.friend = friend; }

}
