package backend.quadcount.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDto {

    @NotBlank
    private String name;
    @Positive
    private double amount;
    private String description;

    @NotBlank
    private String type;          // e.g. FOOD, RENT
    @NotNull
    private Long   groupId;
    @NotNull
    private Long   userId;
}