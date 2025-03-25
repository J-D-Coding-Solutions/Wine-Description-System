package com.example.springboottutorial.Controller.DTO;


public class WineSearchRequest {

    private String description;

    public WineSearchRequest() {
    }

    public WineSearchRequest(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
