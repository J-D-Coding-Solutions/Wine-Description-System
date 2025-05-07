package com.example.springboottutorial.Model;
/**
 * WineRequests.java
 * This class represents a wine request entity in the database.
 */

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wine_requests")//sets the table
public class WineRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wine_request_id;

    private String wineName;

    private String wine_desc;

    @ManyToOne(fetch = FetchType.LAZY)//This means that it will only grab the usernamd when it is asked for and not when the code is ran (It could be EAGER and that has it run immediately)
    @JoinColumn(name = "username", nullable = false, referencedColumnName = "username") //first lists what the collumn is called in the winerequests database and then the second is the name of the collumn in the users database
    private users user;//the user object is the one that is being referenced in the database

    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    public WineRequests() {}

    public WineRequests(String wineName, String wine_desc, users user) {
        this.wineName = wineName;
        this.wine_desc = wine_desc;
        this.user = user;
        this.created_at = LocalDateTime.now();
    }

    @PrePersist//this says as soon as this is ran get a timestamp and put it in the created_at collumn
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    public Long getId() { return wine_request_id; }
    public String getWineName() { return wineName; }//
    public String getWineDesc() { return wine_desc; }
    public users getUser() { return user; }
    public LocalDateTime getCreatedAt() { return created_at; }

    public void setId(Long wine_request_id) { this.wine_request_id = wine_request_id; }
    public void setWineName(String wine_name) { this.wineName = wine_name; }
    public void setWineDesc(String wine_desc) { this.wine_desc = wine_desc; }
    public void setUser(users user) { this.user = user; }
    public void setCreatedAt(LocalDateTime created_at) { this.created_at = created_at; }
}
