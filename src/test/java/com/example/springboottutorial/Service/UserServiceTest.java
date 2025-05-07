package com.example.springboottutorial.Service;

import com.example.springboottutorial.Encryption.PassEncryption;
import com.example.springboottutorial.Model.users;
import com.example.springboottutorial.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService();
        // Use reflection or constructor to inject mocked repository if needed
        // Assuming field injection:
        java.lang.reflect.Field field;
        try {
            field = UserService.class.getDeclaredField("userRepository");
            field.setAccessible(true);
            field.set(userService, userRepository);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAuthenticate_successfulLogin_returnsRole() {
        // Arrange
        users testUser = new users();
        testUser.setUsername("testuser");
        testUser.setPassword("hashedpassword");
        testUser.setRole("admin");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        // Simulate PassEncryption CheckPass
        mockStatic(PassEncryption.class).when(() -> PassEncryption.CheckPass("plaintext", "hashedpassword")).thenReturn(true);

        // Act
        String result = userService.authenticate("testuser", "plaintext");

        // Assert
        assertEquals("admin", result);
        System.out.println("Successful Login returns role Test Successful!");
        System.out.println("Username: " + testUser.getUsername());
        System.out.println("Role: " + result);
    }

    @Test
    public void testAuthenticate_failedLogin_returnsNull() {
        // Arrange
        when(userRepository.findByUsername("wronguser")).thenReturn(Optional.empty());

        // Act
        String result = userService.authenticate("wronguser", "anything");

        // Assert
        assertNull(result);
        System.out.println("Failed Login returns null Test Successful!");
        System.out.println("Username: " + "wronguser");
        System.out.println("Role: " + result);
    }

    @Test
    public void testRegister_userDoesNotExist_returnsTrue() {
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        assertTrue(userService.register("newuser"));
        System.out.println("New User Registration Test Successful!");
        System.out.println("Username: " + "newuser");
        System.out.println("Role: " + "USER");
    }

    @Test
    public void testRegister_userExists_returnsFalse() {
        users existingUser = new users();
        existingUser.setUsername("existinguser");
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(existingUser));

        assertFalse(userService.register("existinguser"));
        System.out.println("Existing User Registration Test Successful!");
        System.out.println("Username: " + "existinguser");
        System.out.println(userService.register("existinguser"));
    }
}