package com.halcyon.mention.service.user;

import com.halcyon.mention.model.User;
import com.halcyon.mention.repository.IUserRepository;
import com.halcyon.mention.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this id not found."));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this email not found"));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User updateFirstnameByEmail(Long id, String firstname) {
        User user = findByEmail(getAuth().getEmail());

        userRepository.updateFirstnameById(firstname, id);
        user.setFirstname(firstname);

        return user;
    }

    public User updateLastnameByEmail(Long id, String lastname) {
        User user = findByEmail(getAuth().getEmail());

        userRepository.updateLastnameById(lastname, id);
        user.setLastname(lastname);

        return user;
    }

    private JwtAuthentication getAuth() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}