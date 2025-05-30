package backend.quadcount.repository;

import backend.quadcount.model.Expense;
import backend.quadcount.model.Group;
import backend.quadcount.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional @Rollback
@ActiveProfiles("test")   // <--- Add this line

class ExpenseRepositoryTest {

    @Autowired ExpenseRepository expenseRepo;
    @Autowired GroupRepository   groupRepo;
    @Autowired UserRepository    userRepo;

    @Test
    void findByGroupId_returns_only_that_groups_expenses() {
        // Arrange: create 1 group, 1 user, 2 expenses
        Group g1 = groupRepo.save(new Group(null, "Trip", null));   // ctor generated by Lombok
        Group g2 = groupRepo.save(new Group(null, "Roommates", null));
        User  u  = userRepo.save(new User(null, "bob@test.com", "pw", "Bob", null,null));

        Expense a = new Expense(null,"Dinner",20,"", "FOOD", false, g1, u);
        Expense b = new Expense(null,"Taxi"  ,30,"", "TRAVEL", false, g2, u);
        expenseRepo.saveAll(List.of(a, b));

        // Act
        List<Expense> result = expenseRepo.findByGroupId(g1.getId());

        // Assert
        assertThat(result).hasSize(1)
                .allMatch(e -> e.getGroup().getId().equals(g1.getId()));
    }
}
