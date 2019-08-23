package com.werner.billtask.model;

import java.util.List;

public class Person extends PersonDescription {

	private List<Expense> expenseList;
	
	public Person() {}

	public Person(Long id, String name, String number, List<Expense> expenseList) {
		super(id, name, number);
		this.expenseList = expenseList;
	}

	public List<Expense> getExpenseList() {
		return expenseList;
	}

	public void setExpenseList(List<Expense> expenseList) {
		this.expenseList = expenseList;
	}

}
