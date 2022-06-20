package by.it.academy.account_service.services.api;

import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.models.dto.Operation;
import by.it.academy.account_service.models.dto.PageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IOperationService {

    /**
     * Saving operation in database
     * @param operation param for saving
     * @param account the account to which the operation belongs
     * @return saved operation from database
     */
    Operation create(Operation operation, Account account);

    /**
     * Getting page of operations
     * @param account the account to which the operation belongs
     * @param pageable param for pagination
     * @return the page with operations
     */
    PageDTO<Operation> getPage(Account account, Pageable pageable);

    /**
     * Getting operation from database by id
     * @param uuid operation's id
     * @return operation from database
     */
    Operation get(UUID uuid);

    /**
     * Getting list of operations from database
     * @param account the account to which the operation belongs
     * @return list of operations from database
     */
    List<Operation> getAll(Account account);

    /**
     * Updating operation
     * @param uuid operation's id
     * @param dtUpdate update time for optimistic lock
     * @param updatedOperation updated operation
     * @return updated operation from database
     */
    Operation update(UUID uuid, long dtUpdate, Operation updatedOperation);

    /**
     * Deleting operation by id
     * @param uuid operation's id
     * @param dtUpdate update time for optimistic lock
     */
    void delete(UUID uuid, long dtUpdate);

    /**
     * Deleting operations by account
     * @param account the account to which the operation belongs
     * @param dtUpdate update time for optimistic lock
     */
    void deleteByAccount(Account account, long dtUpdate);

    /**
     * Getting operation count by account's id
     * @param accountUUID account's id
     * @return count of operations
     */
    long getCountByAccountUUID(UUID accountUUID);

}
