package com.example.springboottutorial.Controller.DTO;

/**
 * This class is used to transfer data between the client and server.
 * It contains the fields that are needed to create a new wine search request.
 * It is used in the WineController class.
 *
 *
 *
 */


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
