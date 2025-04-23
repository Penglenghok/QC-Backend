package backend.quadcount.service;


import backend.quadcount.model.Expense;
import backend.quadcount.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense expenseDetails) {
        return expenseRepository.findById(id).map(expense -> {
            expense.setName(expenseDetails.getName());
            expense.setAmount(expenseDetails.getAmount());
            expense.setDescription(expenseDetails.getDescription());
            expense.setType(expenseDetails.getType());
            expense.set_settle(expenseDetails.is_settle());
            expense.setGroup(expenseDetails.getGroup());
            expense.setUser(expenseDetails.getUser());
            return expenseRepository.save(expense);
        }).orElseThrow(() -> new RuntimeException("Expense not found"));
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> getExpensesByGroupId(Long groupId) {
        return expenseRepository.findByGroupId(groupId);
    }
}