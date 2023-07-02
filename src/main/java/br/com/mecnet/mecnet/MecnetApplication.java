package br.com.mecnet.mecnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MecnetApplication {
	public static void main(String[] args) {
		SpringApplication.run(MecnetApplication.class, args);
	}

}
