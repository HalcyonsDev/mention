package com.halcyon.mention.controller;

import com.halcyon.mention.model.User;
import com.halcyon.mention.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(
            summary = "find and return user by his id",
            description = "get user by id"
    )
    public ResponseEntity<User> getById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update-firstname")
    @Operation(
            summary = "find and update user's firstname",
            description = "update firstname"
    )
    public ResponseEntity<User> updateFirstname(
            @RequestParam
            @Size(min = 2, max = 50, message = "Firstname must be more than 1 character and less than 50 characters.") String value
    ) {
        User updatedUser = userService.updateFirstname(value);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/update-lastname")
    @Operation(
            summary = "find and update user's lastname",
            description = "update lastname"
    )
    public ResponseEntity<User> updateLastname(
            @RequestParam
            @Size(min = 2, max = 50, message = "Lastname must be more than 1 character and less than 50 characters.") String value
    ) {
        User updatedUser = userService.updateLastname(value);
        return ResponseEntity.ok(updatedUser);
    }
}