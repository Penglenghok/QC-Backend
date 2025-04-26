package backend.quadcount.service;

import backend.quadcount.dto.ExpenseRequestDto;
import backend.quadcount.model.*;
import backend.quadcount.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")   // <--- Add this line

class ExpenseServiceTest {

    @Mock ExpenseRepository  expenseRepo;
    @Mock GroupRepository    groupRepo;
    @Mock UserRepository     userRepo;

    @InjectMocks ExpenseService expenseService;

    Group group;
    User  user;

    @BeforeEach
    void setUp() {
        group = new Group(1L,"Trip",null);
        user  = new User (1L,"bob@test.com","pw","Bob",null,null);
    }

    @Test
    void createExpense_saves_and_returns_entity() {
        // Arrange
        ExpenseRequestDto dto = new ExpenseRequestDto(
                "Dinner", 25, "food", "FOOD", group.getId(), user.getId());

        when(groupRepo.findById(String.valueOf(group.getId()))).thenReturn(java.util.Optional.of(group));
        when(userRepo.findById(user.getId())).thenReturn(java.util.Optional.of(user));

        // stub save → give it an ID
        ArgumentCaptor<Expense> cap = ArgumentCaptor.forClass(Expense.class);
        when(expenseRepo.save(cap.capture()))
                .thenAnswer(inv -> { Expense e = cap.getValue(); e.setId(99L); return e; });

        // Act
        Expense saved = expenseService.createExpense(dto);

        // Assert
        assertThat(saved.getId()).isEqualTo(99L);
        assertThat(saved.getName()).isEqualTo(dto.getName());
        verify(expenseRepo, times(1)).save(any(Expense.class));
    }
}
