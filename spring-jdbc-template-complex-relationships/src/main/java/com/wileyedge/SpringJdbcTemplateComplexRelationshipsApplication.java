package com.wileyedge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.wileyedge.controller.MeetingController;

@SpringBootApplication
public class SpringJdbcTemplateComplexRelationshipsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcTemplateComplexRelationshipsApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(MeetingController controller) {
		return args -> {
			controller.run();
		};
	}

}
