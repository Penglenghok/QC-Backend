package backend.quadcount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequestDto {

    @NotBlank
    private String name;
    @Size(min = 1, message = "At least one user must be in the group")
    private List<Long> userIds;           // IDs of existing users
}