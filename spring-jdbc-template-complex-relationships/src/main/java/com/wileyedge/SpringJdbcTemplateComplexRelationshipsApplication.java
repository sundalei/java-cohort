package com.wileyedge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wileyedge.controller.MeetingController;

@SpringBootApplication
public class SpringJdbcTemplateComplexRelationshipsApplication implements CommandLineRunner {

	private final MeetingController meetingController;

	public SpringJdbcTemplateComplexRelationshipsApplication(MeetingController meetingController) {
		this.meetingController = meetingController;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcTemplateComplexRelationshipsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		meetingController.run();
	}

}
