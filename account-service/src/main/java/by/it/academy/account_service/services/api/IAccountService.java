package by.it.academy.account_service.services.api;

import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.models.dto.PageDTO;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IAccountService {

    /**
     * Saving account in database
     * @param account param for saving
     * @return saved account from database
     */
    Account create(Account account);

    /**
     * Getting page of accounts from database
     * @param pageable param for pagination
     * @return the page with accounts
     */
    PageDTO<Account> getPage(Pageable pageable);

    /**
     * Getting account from database
     * @param uuid account's id
     * @return account from database
     */
    Account get(UUID uuid);

    /**
     * Account updating
     * @param uuid account's id
     * @param dtUpdate update time for optimistic lock
     * @param updatedAccount updated account
     * @return updated account from database
     */
    Account update(UUID uuid, long dtUpdate, Account updatedAccount);

    /**
     * Account deleting
     * @param uuid account's id
     * @param dtUpdate update time for optimistic lock
     */
    void delete(UUID uuid, long dtUpdate);

}
