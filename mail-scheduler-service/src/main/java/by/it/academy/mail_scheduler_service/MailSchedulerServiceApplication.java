package by.it.academy.mail_scheduler_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by.it.academy.mail_scheduler_service.repositories.api")
public class MailSchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailSchedulerServiceApplication.class, args);
	}

}
