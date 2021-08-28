package ru.hiddenproject.feelmeserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application's class
 */
@SpringBootApplication
public class FeelMeServerApplication {

	/**
	 * Starts application
	 * @param args Command line args
	 */
	public static void main(String[] args) {
		SpringApplication.run(FeelMeServerApplication.class, args);
	}

}
