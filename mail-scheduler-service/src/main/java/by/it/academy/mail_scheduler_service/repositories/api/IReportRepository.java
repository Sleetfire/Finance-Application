package by.it.academy.mail_scheduler_service.repositories.api;

import by.it.academy.mail_scheduler_service.repositories.entities.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IReportRepository extends JpaRepository<ReportEntity, UUID> {
}
