package com.werner.billtask.configuration;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.werner.billtask.model.Bill;
import com.werner.billtask.model.Person;

@Configuration
@EnableTask
@EnableBatchProcessing
public class BillingConfiguration {

	@Value("${async.thread.max.pool}")
	private Integer maxPoolSize;

	@Value("${async.thread.core.pool}")
	private Integer corePoolSize;

	@Value("${async.thread.queue}")
	private Integer queueSize;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean(name = "asyncExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueSize);
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setThreadNamePrefix("AsyncExecutor-");
		return executor;
	}

	@Bean
	public Job job1(ItemReader<Person> reader, ItemProcessor<Person, Future<Bill>> itemProcessor,
			ItemWriter<Future<Bill>> writer) {
		Step step = stepBuilderFactory.get("BillProcessing") //
				.<Person, Future<Bill>>chunk(corePoolSize) //
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
