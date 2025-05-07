package com.example.springboottutorial.Controller.DTO;

/**
 * This class is used to transfer data between the client and server.
 * It contains the fields that are needed to create a new friend favorite.
 * It is used in the FriendController class.
 *
 *
 *
 */
public class friendFavDTO {
        private String wineName;
        private String wineDesc;

        public friendFavDTO(String wineName, String wineDesc) {
            this.wineName = wineName;
            this.wineDesc = wineDesc;
        }

        // Getters and setters
        public String getWineName() { return wineName; }
        public void setWineName(String wineName) { this.wineName = wineName; }

        public String getWineDesc() { return wineDesc; }
        public void setWineDesc(String wineDesc) { this.wineDesc = wineDesc; }
}


