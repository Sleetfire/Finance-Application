package by.it.academy.account_scheduler_service.repositories.api;

import by.it.academy.account_scheduler_service.repositories.entities.OperationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOperationRepository extends JpaRepository<OperationEntity, UUID> {
}
