package backend.quadcount.repository;

import backend.quadcount.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByGroupId(Long groupId);
    Page<Expense> findByGroupId(Long groupId, Pageable pageable);

}