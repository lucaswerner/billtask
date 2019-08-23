package com.werner.billtask.writer;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.werner.billtask.model.Bill;

@Component
public class BillWriter {
	
	@Bean
	public ItemWriter<Bill> jdbcBillWriter(DataSource dataSource) {
		return new JdbcBatchItemWriterBuilder<Bill>()
				.beanMapped()
				.dataSource(dataSource)
				.sql("INSERT INTO BILL_STATEMENTS (id, name, number, total) VALUES (:id, :name, :number, :total)")
				.build();
	}
}
