package backend.quadcount.controller;

import backend.quadcount.api.ResponseUtil;
import backend.quadcount.dto.ExpenseRequestDto;
import backend.quadcount.model.Expense;
import backend.quadcount.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    /* list with paging */
    @GetMapping
    public ResponseEntity<?> getAllExpenses(Pageable pageable) {
        Page<Expense> page = expenseService.getAllExpenses(pageable);
        return ResponseUtil.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getExpenseById(@PathVariable Long id) {
        return ResponseUtil.ok(expenseService.getExpenseById(id));
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<?> getExpenseByGroupId(@PathVariable Long id) {
        return ResponseUtil.ok(expenseService.getExpensesByGroupId(id));
    }

    @PostMapping
    public ResponseEntity<?> createExpense(@Valid @RequestBody ExpenseRequestDto dto) {
        return ResponseUtil.created(expenseService.createExpense(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateExpense(@PathVariable Long id,
                                           @Valid @RequestBody ExpenseRequestDto dto) {
        return ResponseUtil.ok(expenseService.updateExpense(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseUtil.ok("Deleted");
    }
}
