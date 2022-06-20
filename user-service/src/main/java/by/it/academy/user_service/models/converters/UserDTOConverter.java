package by.it.academy.user_service.models.converters;

import by.it.academy.user_service.models.dto.User;
import by.it.academy.user_service.repositories.entities.UserEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDTOConverter implements Converter<UserEntity, User> {
    @Override
    public User convert(UserEntity source) {
        return User.Builder.createBuilder()
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
    public <U> Converter<UserEntity, U> andThen(Converter<? super User, ? extends U> after) {
        return Converter.super.andThen(after);
    }
}
