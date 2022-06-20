package by.it.academy.classifier_service.services;

import by.it.academy.classifier_service.exception.EssenceNotFoundException;
import by.it.academy.classifier_service.exception.ValidationException;
import by.it.academy.classifier_service.models.dto.Currency;
import by.it.academy.classifier_service.models.dto.PageDTO;
import by.it.academy.classifier_service.models.errors.ValidationError;
import by.it.academy.classifier_service.repositories.api.ICurrencyRepository;
import by.it.academy.classifier_service.repositories.entities.CurrencyEntity;
import by.it.academy.classifier_service.services.api.ICurrencyService;
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
public class CurrencyService implements ICurrencyService {

    private final ICurrencyRepository currencyRepository;
    private final ConversionService conversionService;
    private static final Logger logger = LogManager.getLogger(CurrencyService.class);

    public CurrencyService(ICurrencyRepository currencyRepository, ConversionService conversionService) {
        this.currencyRepository = currencyRepository;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public Currency create(Currency currency) {
        this.checkCurrency(currency);
        LocalDateTime dtNow = LocalDateTime.now();
        currency.setDtCreate(dtNow);
        currency.setDtUpdate(dtNow);
        CurrencyEntity currencyEntity = this.conversionService.convert(currency, CurrencyEntity.class);
        if (currencyEntity != null) {
            currencyEntity = this.currencyRepository.save(currencyEntity);
            logger.info("In database was created currency with title {}", currencyEntity.getTitle());
        }
        return this.conversionService.convert(currencyEntity, Currency.class);
    }

    @Override
    public Currency get(UUID uuid) {
        Optional<CurrencyEntity> optionalCurrencyEntity = this.currencyRepository.findById(uuid);
        if (optionalCurrencyEntity.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalCurrencyEntity.get(), Currency.class);
    }

    @Override
    public PageDTO<Currency> getPage(Pageable pageable) {
        Page<CurrencyEntity> page = this.currencyRepository.findAll(pageable);
        List<CurrencyEntity> currencyEntityList = page.getContent();
        if (currencyEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essences are not exist");
        }
        List<Currency> currencyList = new ArrayList<>();
        currencyEntityList.forEach(currencyEntity -> currencyList.add(this.conversionService.convert(currencyEntity, Currency.class)));
        return PageDTO.Builder.createBuilder(Currency.class)
                .setNumber(page.getNumber())
                .setSize(page.getSize())
                .setTotalPages(page.getTotalPages())
                .setTotalElements(Math.toIntExact(page.getTotalElements()))
                .setFirst(page.isFirst())
                .setNumberOfElements(page.getNumberOfElements())
                .setLast(page.isLast())
                .setContent(currencyList)
                .build();
    }

    @Override
    public List<Currency> getAll() {
        List<CurrencyEntity> currencyEntityList = this.currencyRepository.findAll();
        List<Currency> currencyList = new ArrayList<>();
        if (currencyEntityList.isEmpty()) {
            throw new EssenceNotFoundException("Essences is not exist");
        }
        currencyEntityList.forEach(currencyEntity -> currencyList.add(this.conversionService.convert(currencyEntity, Currency.class)));
        return currencyList;
    }

    private void checkCurrency(Currency currency) {
        List<ValidationError> errors = new ArrayList<>();
        if (this.isNullOrEmpty(currency.getTitle())) {
            errors.add(new ValidationError("title", "Title is null or empty"));
        }
        if (this.isNullOrEmpty(currency.getDescription())) {
            errors.add(new ValidationError("description", "Description is null or empty"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Currency's validation error", errors);
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
