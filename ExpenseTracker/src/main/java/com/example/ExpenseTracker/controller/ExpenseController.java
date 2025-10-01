package com.example.ExpenseTracker.controller;

import com.example.ExpenseTracker.model.Expense;
import com.example.ExpenseTracker.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
	private final ExpenseService service;
	
	public ExpenseController(ExpenseService service) {
		this.service = service;
	}
	
	@GetMapping
	public List<Expense> list(){
		return service.getAllExpenses();
	}
	
	@GetMapping("/{id}")
	public Expense get(@PathVariable Long id) {
		return service.getExpense(id);
	}
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody Expense expense){
		service.addExpense(expense);
		return ResponseEntity.created(URI.create("/api/expenses")).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Expense expense){
		expense.setId(id);
		service.updateExpense(expense);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deleteExpense(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/summary")
	public ResponseEntity<Map<String,Object>> monthlySummary(@RequestParam int year, @RequestParam int month) {
	    BigDecimal total = service.getMonthlyTotal(year, month);
	    Map<String,Object> resp = new HashMap<>();
	    resp.put("year", year);
	    resp.put("month", month);
	    resp.put("total", total);
	    return ResponseEntity.ok(resp);
	}

}
