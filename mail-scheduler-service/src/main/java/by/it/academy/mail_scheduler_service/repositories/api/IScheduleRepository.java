package by.it.academy.mail_scheduler_service.repositories.api;

import by.it.academy.mail_scheduler_service.repositories.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IScheduleRepository extends JpaRepository<ScheduleEntity, UUID> {
}
