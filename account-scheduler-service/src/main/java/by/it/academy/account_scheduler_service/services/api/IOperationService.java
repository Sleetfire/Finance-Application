package by.it.academy.account_scheduler_service.services.api;

import by.it.academy.account_scheduler_service.models.dto.Operation;

import java.util.List;
import java.util.UUID;

public interface IOperationService {

    /**
     * Creating operation in database
     * @param operation operation dto
     * @return created operation from database
     */
    Operation create(Operation operation);

    /**
     * Getting operation from database
     * @param uuid operation's uuid
     * @return operation from database
     */
    Operation get(UUID uuid);

    /**
     * Getting list of all operations
     * @return list of operations
     */
    List<Operation> getAll();

    /**
     * Updating operation
     * @param uuid operation's uuid
     * @param dtUpdate  operation's update date-time
     * @param updatedOperation operation with updated fields
     * @return updated operation from database
     */
    Operation update(UUID uuid, long dtUpdate, Operation updatedOperation);

    /**
     * Deleting operation
     * @param uuid operation's uuid
     * @param dtUpdate operation's update date-time
     */
    void delete(UUID uuid, long dtUpdate);

}
