package backend.quadcount.controller;

import backend.quadcount.api.ResponseUtil;

import backend.quadcount.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getUsers() {
        return ResponseUtil.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseUtil.ok(
                userRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("User not found"))
        );
    }
}
