package by.it.academy.account_service.repositories.api;

import by.it.academy.account_service.repositories.entities.AccountEntity;
import by.it.academy.account_service.repositories.entities.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOperationRepository extends JpaRepository<OperationEntity, UUID> {

    /**
     * Method for getting page of operation entities
     *
     * @param accountEntity account entity's search param
     * @param username      user's identification for authorization
     * @param pageable      param for pagination
     * @return the page with operation entities
     */
    Page<OperationEntity> findByAccountEntityAndUsername(AccountEntity accountEntity, String username, Pageable pageable);

    /**
     * Method for getting list of operation entities
     *
     * @param accountEntity account entity's search param
     * @param username      user's identification for authorization
     * @return the list with operation entities
     */
    List<OperationEntity> findAllByAccountEntityAndUsername(AccountEntity accountEntity, String username);

    /**
     * Method for getting wrapped operation entity
     * @param uuid operation's id
     * @param username user's identification for authorization
     * @return the operation entity wrapped in Optional
     */
    Optional<OperationEntity> findByUuidAndUsername(UUID uuid, String username);

    /**
     * Method for deleting entity
     * @param accountEntity account entity's search param
     * @param username user's identification for authorization
     */
    void deleteAllByAccountEntityAndUsername(AccountEntity accountEntity, String username);

    /**
     * Method for calculating the number of operations
     * @param uuid account id
     * @param username user's identification for authorization
     * @return count of operations for current account
     */
    long countOperationEntitiesByAccountEntity_UuidAndUsername(UUID uuid, String username);

}
