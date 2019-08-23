package com.werner.billtask.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.werner.billtask.model.Bill;
import com.werner.billtask.model.Person;

@Configuration
@EnableTask
@EnableBatchProcessing
public class BillingConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job job1(ItemReader<Person> reader, ItemProcessor<Person, Bill> itemProcessor, ItemWriter<Bill> writer) {
		Step step = stepBuilderFactory.get("BillProcessing") //
				.<Person, Bill>chunk(1) //
				.reader(reader) //
				.processor(itemProcessor) //
				.writer(writer) //
				.build();

		return jobBuilderFactory.get("BillJob") //
				.incrementer(new RunIdIncrementer()) //
				.start(step) //
				.build();
	}
}
