package by.it.academy.user_service.models.converters;

import by.it.academy.user_service.models.dto.User;
import by.it.academy.user_service.repositories.entities.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserEntityConverter implements Converter<User, UserEntity> {

    @Override
    public UserEntity convert(User source) {
        return UserEntity.Builder.createBuilder()
                .setUuid(source.getUuid())
                .setDtCreate(source.getDtCreate())
                .setDtUpdate(source.getDtUpdate())
                .setName(source.getName())
                .setUsername(source.getUsername())
                .setPassword(source.getPassword())
                .setRole(source.getRole())
                .setEnabled(source.isEnabled())
                .setVerifyCode(source.getVerifyCode())
                .build();
    }

    @Override
    public <U> Converter<User, U> andThen(Converter<? super UserEntity, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
