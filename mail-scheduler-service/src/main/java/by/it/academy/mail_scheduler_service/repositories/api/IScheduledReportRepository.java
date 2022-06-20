package by.it.academy.mail_scheduler_service.repositories.api;

import by.it.academy.mail_scheduler_service.repositories.entities.ScheduledReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IScheduledReportRepository extends JpaRepository<ScheduledReportEntity, UUID> {

    Page<ScheduledReportEntity> findAllByUsername(Pageable pageable, String username);

}
