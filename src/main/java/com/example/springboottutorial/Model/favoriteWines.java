package com.example.springboottutorial.Model;

import jakarta.persistence.*;

@Entity
public class favoriteWines {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private users user;

    @ManyToOne
    @JoinColumn(name = "wine_id", nullable = false, referencedColumnName = "wine_id")
    private wines wine;

    public favoriteWines() {}

    public favoriteWines(users user, wines wine) {
        this.user = user;
        this.wine = wine;
    }

    public Long getId() {return this.id;}
    public users getUser(){return this.user;}
    public wines getWine(){return this.wine;}

    public void setUser(users user) { this.user = user; }
    public void setWine(wines wine) { this.wine = wine; }
}
