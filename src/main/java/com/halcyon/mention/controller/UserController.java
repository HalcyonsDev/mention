package com.halcyon.mention.controller;

import com.halcyon.mention.model.User;
import com.halcyon.mention.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update-firstname/{userId}")
    public ResponseEntity<User> updateFirstname(@PathVariable Long userId, String firstname) {
        User updatedUser = userService.updateFirstnameByEmail(userId, firstname);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/update-lastname/{userId}")
    public ResponseEntity<User> updateLastname(@PathVariable Long userId, String lastname) {
        User updatedUser = userService.updateLastnameByEmail(userId, lastname);
        return ResponseEntity.ok(updatedUser);
    }
}