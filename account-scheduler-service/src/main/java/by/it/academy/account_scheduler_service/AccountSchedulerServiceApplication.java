package by.it.academy.account_scheduler_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "by.it.academy.account_scheduler_service.repositories.api")
public class AccountSchedulerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountSchedulerServiceApplication.class, args);
	}

}
