package by.it.academy.account_service.repositories.api;

import by.it.academy.account_service.repositories.entities.BalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IBalanceRepository extends JpaRepository<BalanceEntity, UUID> {
}
