package com.werner.billtask.reader;

import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.werner.billtask.model.Person;

@Component
public class PersonReader {

	@Value("${usage.file.name:classpath:personExpensesMock.json}")
	private Resource usageResource;

	@Bean
	public JsonItemReader<Person> jsonItemReader() {

		ObjectMapper objectMapper = new ObjectMapper();
		JacksonJsonObjectReader<Person> jsonObjectReader = new JacksonJsonObjectReader<>(Person.class);
		jsonObjectReader.setMapper(objectMapper);

		return new JsonItemReaderBuilder<Person>() //
				.jsonObjectReader(jsonObjectReader) //
				.resource(usageResource) //
				.name("PersonJsonItemReader") //
				.build();
	}

}
