package com.example.springboottutorial.Controller.DTO;


public class WineSearchRequest {

    private String combinedInput;

    public WineSearchRequest() {
    }

    public WineSearchRequest(String combinedInput) {
        this.combinedInput = combinedInput;
    }

    public String getCombinedInput() {
        return combinedInput;
    }

    public void setCombinedInput(String combinedInput) {
        this.combinedInput = combinedInput;
    }


}
