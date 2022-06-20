package by.it.academy.classifier_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by.it.academy.classifier_service.repositories.api")
public class ClassifierServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClassifierServiceApplication.class, args);
	}

}
