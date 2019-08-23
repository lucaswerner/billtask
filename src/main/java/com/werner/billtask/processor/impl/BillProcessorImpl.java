package com.werner.billtask.processor.impl;

import org.springframework.batch.item.ItemProcessor;

import com.werner.billtask.model.Bill;
import com.werner.billtask.model.Person;

public class BillProcessorImpl implements ItemProcessor<Person, Bill> {

	@Override
	public Bill process(Person person) throws Exception {

		return new Bill(person.getId(), person.getName(), person.getNumber(), person.getExpenseList().stream()
				.map(expense -> expense.getValue()).mapToDouble(Double::doubleValue).sum());
	}

}
