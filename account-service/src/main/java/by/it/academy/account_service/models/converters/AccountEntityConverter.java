package by.it.academy.account_service.models.converters;

import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.repositories.entities.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountEntityConverter implements Converter<Account, AccountEntity> {
    @Override
    public AccountEntity convert(Account source) {
        return AccountEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setTitle(source.getTitle())
                .setDescription(source.getDescription())
                .setBalanceEntity(new BalanceEntityConverter().convert(source.getBalance()))
                .setType(source.getType())
                .setCurrency(source.getCurrency())
                .setUsername(source.getUsername())
                .build();
    }

    @Override
    public <U> Converter<Account, U> andThen(Converter<? super AccountEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
