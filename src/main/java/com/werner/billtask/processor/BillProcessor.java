package com.werner.billtask.processor;

import org.springframework.batch.integration.async.AsyncItemProcessor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

import com.werner.billtask.model.Bill;
import com.werner.billtask.model.Person;
import com.werner.billtask.processor.impl.BillProcessorImpl;

@Component
public class BillProcessor {

	@Bean
	public ItemProcessor<Person, Future<Bill>> asyncItemProcessor(
			@Qualifier("asyncExecutor") TaskExecutor taskExecutor) {
		AsyncItemProcessor<Person, Bill> asyncItemProcessor = new AsyncItemProcessor<Person, Bill>();
		asyncItemProcessor.setDelegate(new BillProcessorImpl());
		asyncItemProcessor.setTaskExecutor(taskExecutor);
		return asyncItemProcessor;
	}

}
