package by.it.academy.classifier_service.repositories.api;

import by.it.academy.classifier_service.repositories.entities.OperationCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOperationCategoryRepository extends JpaRepository<OperationCategoryEntity, UUID> {
}
