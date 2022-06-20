package by.it.academy.classifier_service.services;

import by.it.academy.classifier_service.exception.EssenceNotFoundException;
import by.it.academy.classifier_service.exception.ValidationException;
import by.it.academy.classifier_service.models.dto.OperationCategory;
import by.it.academy.classifier_service.models.dto.PageDTO;
import by.it.academy.classifier_service.models.errors.ValidationError;
import by.it.academy.classifier_service.repositories.api.IOperationCategoryRepository;
import by.it.academy.classifier_service.repositories.entities.OperationCategoryEntity;
import by.it.academy.classifier_service.services.api.IOperationCategoryService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OperationCategoryService implements IOperationCategoryService {

    private final IOperationCategoryRepository operationCategoryRepository;
    private final ConversionService conversionService;
    private static final Logger logger = LogManager.getLogger(OperationCategoryService.class);

    public OperationCategoryService(IOperationCategoryRepository operationCategoryRepository, ConversionService conversionService) {
        this.operationCategoryRepository = operationCategoryRepository;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public OperationCategory create(OperationCategory operationCategory) {
        this.checkOperationCategory(operationCategory);
        LocalDateTime dtNow = LocalDateTime.now();
        operationCategory.setDtCreate(dtNow);
        operationCategory.setDtUpdate(dtNow);
        OperationCategoryEntity operationCategoryEntity = this.conversionService.convert(operationCategory, OperationCategoryEntity.class);
        if (operationCategoryEntity != null) {
            operationCategoryEntity = this.operationCategoryRepository.save(operationCategoryEntity);
            logger.info("In database was created operation category with title {}", operationCategoryEntity.getTitle());
        }
        return this.conversionService.convert(operationCategoryEntity, OperationCategory.class);
    }

    @Override
    public OperationCategory get(UUID uuid) {
        Optional<OperationCategoryEntity> operationCategoryEntityOptional = this.operationCategoryRepository.findById(uuid);
        if (operationCategoryEntityOptional.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(operationCategoryEntityOptional.get(), OperationCategory.class);
    }

    @Override
    public PageDTO<OperationCategory> getPage(Pageable pageable) {
        Page<OperationCategoryEntity> page = this.operationCategoryRepository.findAll(pageable);
        List<OperationCategoryEntity> operationCategoryEntityList = page.getContent();
        if (operationCategoryEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essences are not exist");
        }
        List<OperationCategory>  operationCategoryList= new ArrayList<>();
        operationCategoryEntityList.forEach(operationCategoryEntity -> operationCategoryList
                .add(this.conversionService.convert(operationCategoryEntity, OperationCategory.class)));
        return PageDTO.Builder.createBuilder(OperationCategory.class)
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(Math.toIntExact(page.getTotalElements()))
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast())
                .setContent(operationCategoryList)
                .build();
    }

    @Override
    public List<OperationCategory> getAll() {
        List<OperationCategoryEntity> operationCategoryEntityList = this.operationCategoryRepository.findAll();
        List<OperationCategory> operationCategoryList = new ArrayList<>();
        if (operationCategoryEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essences is not exist");
        }
        operationCategoryEntityList.forEach(operationCategoryEntity -> operationCategoryList
                .add(this.conversionService.convert(operationCategoryEntity, OperationCategory.class)));
        return operationCategoryList;
    }

    private void checkOperationCategory(OperationCategory operationCategory) {
        List<ValidationError> errors = new ArrayList<>();
        if (this.isNullOrEmpty(operationCategory.getTitle())) {
            errors.add(new ValidationError("title", "Title is null or empty"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Operation category's validation error", errors);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

}
