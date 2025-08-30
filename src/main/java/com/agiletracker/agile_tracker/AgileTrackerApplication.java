package com.agiletracker.agile_tracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgileTrackerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AgileTrackerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Started!!!");
	}

}
