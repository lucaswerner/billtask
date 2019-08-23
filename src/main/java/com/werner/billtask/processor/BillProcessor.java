package com.werner.billtask.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.werner.billtask.model.Bill;
import com.werner.billtask.model.Person;
import com.werner.billtask.processor.impl.BillProcessorImpl;

@Component
public class BillProcessor {

	@Bean
	ItemProcessor<Person, Bill> billProcessorImpl() {
		return new BillProcessorImpl();
	}
}
