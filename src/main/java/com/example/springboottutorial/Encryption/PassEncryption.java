package com.example.springboottutorial.Encryption;
/**
 * PassEncryption.java
 * This class is used to encrypt and check passwords using BCrypt.
 */

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PassEncryption {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String EncyptPassword(String password) {
        return encoder.encode(password);
    }

    public static Boolean CheckPass(String password, String encyrptedPassword) {
        if (encoder.matches(password, encyrptedPassword))
            return true;
        return false;
    }


}
