package com.example.springboottutorial.Model;
/**
 * wine.java
 * This class represents a wine entity in the database.
 */

import jakarta.persistence.*;

@Entity
@Table(name = "wines")
public class wines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wine_id;

    @Column(name = "wine_name")
    private String wineName;
    @Column(name = "wine_description", columnDefinition = "TEXT")//JUST SETS THE DATA TYPE TO TEXT, YOU CANT DO IT IN JAVA AND I DONT WNAT THJE CONSOLE BITCHING
    private String wine_description;
    private String country;
    private String province;
    private String region;
    private String variety;
    private String winery;
    private double coSim;

    public wines() {}

    public wines(String wineName, String wine_description, String country, String province, String region, String variety, String winery) {
        this.wineName = wineName;
        this.wine_description = wine_description;
        this.country = country;
        this.province = province;
        this.region = region;
        this.variety = variety;
        this.winery = winery;
        this.coSim = 0.0;
    }

    public Long getId() { return wine_id; }
    public String getWineName() { return wineName; }
    public String getWineDesc() { return wine_description; }
    public String getCountry() { return country; }
    public String getProvince() { return province; }
    public String getRegion() { return region; }
    public String getVariety() { return variety; }
    public String getWinery() { return winery; }
    public double getcoSim() {return coSim;}


    public void setId(Long id) { this.wine_id = id; }
    public void setWineName(String wineName) { this.wineName = wineName; }
    public void setWineDesc(String wine_description) { this.wine_description = wine_description; }
    public void setCountry(String country) { this.country = country; }
    public void setProvince(String province) { this.province = province; }
    public void setRegion(String region) { this.region = region; }
    public void setVariety(String variety) { this.variety = variety; }
    public void setWinery(String winery) { this.winery = winery; }
    public void setcoSim(double coSim) { this.coSim = coSim; }
}
