package com.example.springboottutorial.Model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "friend_Requests",
        uniqueConstraints = @UniqueConstraint(columnNames = {"sender_id", "friend_id"})
)
public class friendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false, referencedColumnName = "user_id")
    private users sender; // always the lower ID

    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false, referencedColumnName = "user_id")
    private users friend; // always the higher ID

    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    public friendRequest() {
    }

    public friendRequest(users sender, users friend) {
        this.sender = sender;
        this.friend = friend;
        this.created_at = LocalDateTime.now();
    }

    @PrePersist//this says as soon as this is ran get a timestamp and put it in the created_at collumn
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    public Long getId() {return this.id;}
    public users getSender(){return this.sender;}
    public users getFriend(){return this.friend;}
    public LocalDateTime getCreatedAt() { return created_at; }

    public void setSender(users sender) { this.sender = sender; }
    public void setFriend(users friend) { this.friend = friend; }


}
