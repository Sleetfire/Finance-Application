package by.it.academy.account_service.repositories.api;

import by.it.academy.account_service.repositories.entities.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAccountRepository extends JpaRepository<AccountEntity, UUID> {

    /**
     * Method for getting the page of account entities from database
     *
     * @param pageable param for pagination
     * @param username user's identification for authorization
     * @return the page with account entities
     */
    Page<AccountEntity> findAllByUsername(Pageable pageable, String username);

    /**
     * Method for getting wrapped account entity from database
     * @param uuid account's id
     * @param username user's identification
     * @return the account entity wrapped in Optional
     */
    Optional<AccountEntity> findByUuidAndUsername(UUID uuid, String username);


}
