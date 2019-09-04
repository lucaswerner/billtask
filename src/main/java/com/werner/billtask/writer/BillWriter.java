package com.werner.billtask.writer;

import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.springframework.batch.integration.async.AsyncItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.werner.billtask.model.Bill;

@Component
public class BillWriter {

	@Bean
	public ItemWriter<Bill> jdbcBillWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Bill>().beanMapped().dataSource(dataSource)
				.sql("INSERT INTO BILL_STATEMENTS (id, name, number, total) VALUES (:id, :name, :number, :total)")
				.build();
	}

	@Bean
	public ItemWriter<Future<Bill>> asyncItemWriter(ItemWriter<Bill> writer) {
		AsyncItemWriter<Bill> asyncItemWriter = new AsyncItemWriter<Bill>();
		asyncItemWriter.setDelegate(writer);
		return asyncItemWriter;
	}
}
