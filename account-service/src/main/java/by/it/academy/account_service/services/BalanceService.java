package by.it.academy.account_service.services;

import by.it.academy.account_service.exception.EssenceDeleteException;
import by.it.academy.account_service.exception.EssenceNotFoundException;
import by.it.academy.account_service.exception.EssenceUpdateException;
import by.it.academy.account_service.models.dto.Balance;
import by.it.academy.account_service.repositories.api.IBalanceRepository;
import by.it.academy.account_service.repositories.entities.BalanceEntity;
import by.it.academy.account_service.services.api.IBalanceService;
import by.it.academy.account_service.utils.DateTimeUtil;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class BalanceService implements IBalanceService {

    private final IBalanceRepository balanceRepository;
    private final ConversionService conversionService;
    private static final Logger logger = LogManager.getLogger(BalanceService.class);

    public BalanceService(IBalanceRepository balanceRepository, ConversionService conversionService) {
        this.balanceRepository = balanceRepository;
        this.conversionService = conversionService;
    }

    @Override
    @Transactional
    public Balance create(Balance balance) {
        if (balance.getDtCreate() == null || balance.getDtUpdate() == null) {
            LocalDateTime dtNow = LocalDateTime.now();
            balance.setDtCreate(dtNow);
            balance.setDtUpdate(dtNow);
            balance.setValue(BigDecimal.ZERO);
        }
        BalanceEntity balanceEntity = this.conversionService.convert(balance, BalanceEntity.class);
        if (balanceEntity != null) {
            balanceEntity = this.balanceRepository.save(balanceEntity);
            logger.info("Balance with uuid {} was create", balanceEntity.getUuid());
        }
        return this.conversionService.convert(balanceEntity, Balance.class);
    }

    @Override
    public Balance get(UUID uuid) {
        Optional<BalanceEntity> optionalBalanceEntity = this.balanceRepository.findById(uuid);
        if (optionalBalanceEntity.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalBalanceEntity.get(), Balance.class);
    }

    @Override
    @Transactional
    public Balance countValue(UUID uuid, long dtUpdate, BigDecimal value) {
        Balance balance = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(balance.getDtUpdate())) {
            throw new EssenceUpdateException("Date-time don't match");
        }
        BalanceEntity balanceEntity = this.conversionService.convert(balance, BalanceEntity.class);
        if (balanceEntity != null) {
            BigDecimal oldValue = balance.getValue();
            BigDecimal newValue = oldValue.add(value);
            balanceEntity.setValue(newValue);
            balanceEntity = this.balanceRepository.save(balanceEntity);
            logger.info("Value in balance with uuid {} was count", balance.getUuid());
        } else {
            throw new EssenceUpdateException("Incorrect balance's value count");
        }
        return this.conversionService.convert(balanceEntity, Balance.class);
    }

    @Override
    @Transactional
    public Balance updateValue(UUID uuid, long dtUpdate, BigDecimal value) {
        Balance balance = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(balance.getDtUpdate())) {
            throw new EssenceUpdateException("Date-time don't match");
        }
        BalanceEntity balanceEntity = this.conversionService.convert(balance, BalanceEntity.class);
        if (balanceEntity != null) {
            balanceEntity.setValue(value);
            balanceEntity = this.balanceRepository.save(balanceEntity);
            logger.info("Value in balance with uuid was update {}", balance.getUuid());
        } else {
            throw new EssenceUpdateException("Incorrect update of the balance value");
        }
        return this.conversionService.convert(balanceEntity, Balance.class);
    }

    @Override
    @Transactional
    public void delete(UUID uuid, long dtUpdate) {
        Balance balance = this.get(uuid);
        if (dtUpdate != DateTimeUtil.convertLocalDateTimeToLong(balance.getDtUpdate())) {
            throw new EssenceDeleteException("Date-time don't match");
        }
        this.balanceRepository.deleteById(uuid);
    }
}
