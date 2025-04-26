package backend.quadcount.dto;
import backend.quadcount.model.User;
import lombok.Data;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Data
public class AuthResponse {

    private String token;
    private User user;
}
