package com.example.tskmngr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TskMngrApplication {

	public static void main(String[] args) {
		SpringApplication.run(TskMngrApplication.class, args);
	}

}
