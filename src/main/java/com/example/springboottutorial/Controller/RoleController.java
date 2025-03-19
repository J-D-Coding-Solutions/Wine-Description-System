package com.example.springboottutorial.Controller;

import com.example.springboottutorial.Model.*;
import com.example.springboottutorial.Controller.*;
import com.example.springboottutorial.Repository.*;
import com.example.springboottutorial.Service.*;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class RoleController {

    @GetMapping("/role")
    public Map<String, String> getUserRole(HttpSession session) {
        String role = (String) session.getAttribute("userRole");

        if (role == null) {
            return Map.of("role", "guest"); // Default to guest if no role is found
        }

        return Map.of("role", role);
    }

    // Logout and clear the user role from the session
    @GetMapping("/logout")
    public Map<String, String> logout(HttpSession session) {
        // Clear the user role from the session
        session.removeAttribute("userRole");

        return Map.of("message", "Logged out successfully", "role", "guest");
    }
}

