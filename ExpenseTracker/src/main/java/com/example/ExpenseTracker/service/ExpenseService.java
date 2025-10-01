package com.example.ExpenseTracker.service;

import com.example.ExpenseTracker.model.Expense;
import com.example.ExpenseTracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.math.BigDecimal;


@Service
public class ExpenseService {
	private final ExpenseRepository repo;
	
	public ExpenseService(ExpenseRepository repo) {
		this.repo = repo;
	}
	
	public List<Expense> getAllExpenses(){
		return repo.findAll();
	}
	
	public Expense getExpense(Long id) {
		return repo.findById(id);
	}
	
	public void addExpense(Expense expense) {
		repo.save(expense);
	}
	
	public void updateExpense(Expense expense) {
		repo.update(expense);
	}
	
	public void deleteExpense(Long id) {
		repo.deleteById(id);
	}
	
	public BigDecimal getMonthlyTotal(int year, int month) {
	    return repo.getMonthlyTotal(year, month);
	}

}
