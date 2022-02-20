package com.ammgroup.sep;

//import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javafx.application.Application;

@SpringBootApplication
@EnableJpaRepositories("com.ammgroup.sep.repository")
//@ComponentScan(basePackages = {"com.ammgroup.sep.controller"})
public class SEPSpringBootApplication {
	
	public static void main(String[] args) {
		
		System.setProperty("java.awt.headless", "false");
		
		//Launch JavaFX environment
		Application.launch(SEPJavaFXApplication.class);

	}

}
