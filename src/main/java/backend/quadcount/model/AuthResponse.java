package backend.quadcount.model;
import lombok.Data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Data
public class AuthResponse {

    private String token;
    private User user;
}
