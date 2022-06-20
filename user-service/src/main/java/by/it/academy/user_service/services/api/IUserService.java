package by.it.academy.user_service.services.api;

import by.it.academy.user_service.models.dto.Login;
import by.it.academy.user_service.models.dto.User;

public interface IUserService {

    /**
     * Creating user in database
     * @param user user dto
     * @return created user from database
     */
    User create(User user);

    /**
     * Log into the system and getting registration token
     * @param login login dto
     * @return registration token
     */
    String login(Login login);

    /**
     * Getting user by username
     * @param username user's username
     * @return user from database
     */
    User getByUsername(String username);

    /**
     * Verification user by special code
     * @param code verify code. It is used uuid
     * @return user from database
     */
    User verify(String code);

}
