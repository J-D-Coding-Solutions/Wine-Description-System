package com.example.springboottutorial.Model;
/**
 * BugReport.java
 * This class represents a bug report entity in the database.
 */

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bug_reports")//sets the table
public class BugReport {

    @Id//Means that this is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Identity means that it will be auto incremented
    private Long bug_report_id;

    @Column(name = "bug_report", columnDefinition = "TEXT")//JUST SETS THE DATA TYPE TO TEXT, YOU CANT DO IT IN JAVA AND I DONT WNAT THJE CONSOLE BITCHING
    private String bug_report;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username", nullable = true, referencedColumnName = "username") //first lists what the collumn is called in the winerequests database and then the second is the name of the collumn in the users database
    private users user;//the user object is the one that is being referenced in the database

    @Column(name = "created_at", updatable = false)
    private LocalDateTime created_at;

    public BugReport() {}

    public BugReport(String bug_report, users user) {
        this.bug_report = bug_report;
        this.user = user;
        this.created_at = LocalDateTime.now();
    }

    @PrePersist//this says as soon as this is ran get a timestamp and put it in the created_at collumn
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }

    public Long getId() { return bug_report_id; }
    public String getBugReport() { return bug_report; }//
    public users getUser() { return user; }
    public LocalDateTime getCreatedAt() { return created_at; }

    public void setId(Long bug_report_id) { this.bug_report_id = bug_report_id; }
    public void setBugReport(String bug_report) { this.bug_report = bug_report; }
    public void setUser(users user) { this.user = user; }
    public void setCreatedAt(LocalDateTime created_at) { this.created_at = created_at; }
}
