package backend.quadcount.controller;

import backend.quadcount.api.ResponseUtil;
import backend.quadcount.dto.AuthRequest;
import backend.quadcount.dto.AuthResponse;
import backend.quadcount.dto.RegisterUserDto;
import backend.quadcount.model.User;
import backend.quadcount.repository.UserRepository;
import backend.quadcount.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUserDto dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseUtil.error(HttpStatus.CONFLICT, "Email is already taken");
        }
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirst_name(dto.getFirst_name());
        user.setLast_name(dto.getLast_name());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return ResponseUtil.created("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(), authRequest.getPassword())
            );
            User user = userRepository.findByEmail(auth.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            String token = jwtTokenUtil.generateToken(user);
            return ResponseUtil.ok(new AuthResponse(token, user));
        } catch (BadCredentialsException e) {
            return ResponseUtil.error(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
}
