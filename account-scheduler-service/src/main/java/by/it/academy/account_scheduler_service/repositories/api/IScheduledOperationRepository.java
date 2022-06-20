package by.it.academy.account_scheduler_service.repositories.api;

import by.it.academy.account_scheduler_service.repositories.entities.ScheduledOperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IScheduledOperationRepository extends JpaRepository<ScheduledOperationEntity, UUID> {

    Optional<ScheduledOperationEntity> findByUuidAndUsername(UUID uuid, String username);

    Page<ScheduledOperationEntity> findAllByUsername(Pageable pageable, String username);


}
