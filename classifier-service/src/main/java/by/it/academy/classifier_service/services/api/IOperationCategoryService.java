package by.it.academy.classifier_service.services.api;

import by.it.academy.classifier_service.models.dto.OperationCategory;
import by.it.academy.classifier_service.models.dto.PageDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IOperationCategoryService {

    /**
     * Creating operation category
     * @param operationCategory operation category's dto
     * @return created operation category from database
     */
    OperationCategory create(OperationCategory operationCategory);

    /**
     * Getting operation category
     * @param uuid operation category's uuid
     * @return operation category from database
     */
    OperationCategory get(UUID uuid);

    /**
     * Getting page of operation categories
     * @param pageable param for pagination
     * @return page of operation category
     */
    PageDTO<OperationCategory> getPage(Pageable pageable);

    /**
     * Getting all operation categories
     * @return list of operation categories
     */
    List<OperationCategory> getAll();

}
