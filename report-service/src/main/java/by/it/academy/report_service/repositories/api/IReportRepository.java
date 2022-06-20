package by.it.academy.report_service.repositories.api;

import by.it.academy.report_service.repositories.entities.ReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IReportRepository extends JpaRepository<ReportEntity, UUID> {

    Page<ReportEntity> findAllByUsername(Pageable pageable, String username);

    List<ReportEntity> findAllByUsername(String username);

}
