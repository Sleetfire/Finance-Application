package by.it.academy.account_service.models.converters;

import by.it.academy.account_service.models.dto.Account;
import by.it.academy.account_service.repositories.entities.AccountEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOConverter implements Converter<AccountEntity, Account> {
    @Override
    public Account convert(AccountEntity source) {
        return Account.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setTitle(source.getTitle())
                .setDescription(source.getDescription())
                .setBalance(new BalanceDTOConverter().convert(source.getBalanceEntity()))
                .setType(source.getType())
                .setCurrency(source.getCurrency())
                .setUsername(source.getUsername())
                .build();
    }

    @Override
    public <U> Converter<AccountEntity, U> andThen(Converter<? super Account, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
