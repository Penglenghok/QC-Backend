package backend.quadcount.service;


import backend.quadcount.dto.ExpenseRequestDto;
import backend.quadcount.model.Expense;
import backend.quadcount.repository.ExpenseRepository;
import backend.quadcount.repository.GroupRepository;
import backend.quadcount.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;



    public Page<Expense> getAllExpenses(Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }


    public Expense createExpense(ExpenseRequestDto dto) {
        Expense e = new Expense();
        e.setName(dto.getName());
        e.setAmount(dto.getAmount());
        e.setDescription(dto.getDescription());
        e.setType(dto.getType());
        e.set_settle(false);

        // fetch refs
        e.setGroup(groupRepository.findById(String.valueOf(dto.getGroupId()))
                .orElseThrow(() -> new EntityNotFoundException("Group not found")));
        e.setUser(userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found")));

        return expenseRepository.save(e);
    }

    public Expense updateExpense(Long id, ExpenseRequestDto dto) {
        return expenseRepository.findById(id).map(e -> {
            e.setName(dto.getName());
            e.setAmount(dto.getAmount());
            e.setDescription(dto.getDescription());
            e.setType(dto.getType());
            return expenseRepository.save(e);
        }).orElseThrow(() -> new EntityNotFoundException("Expense not found"));
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByGroupId(Long groupId) {
        return expenseRepository.findByGroupId(groupId);
    }

    public Page<Expense> getExpensesByGroupId(Long groupId, Pageable pageable) {
        return expenseRepository.findByGroupId(groupId, pageable);
    }
}