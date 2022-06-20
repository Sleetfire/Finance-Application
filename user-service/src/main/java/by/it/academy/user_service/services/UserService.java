package by.it.academy.user_service.services;

import by.it.academy.user_service.controllers.utils.JwtTokenUtil;
import by.it.academy.user_service.exception.*;
import by.it.academy.user_service.models.dto.Login;
import by.it.academy.user_service.models.dto.User;
import by.it.academy.user_service.models.enums.Role;
import by.it.academy.user_service.models.errors.ValidationError;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import by.it.academy.user_service.repositories.api.IUserRepository;
import by.it.academy.user_service.repositories.entities.UserEntity;
import by.it.academy.user_service.services.api.IRequestService;
import by.it.academy.user_service.services.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final ConversionService conversionService;
    private final PasswordEncoder passwordEncoder;
    private final IRequestService requestService;
    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UserService(IUserRepository userRepository, ConversionService conversionService, PasswordEncoder passwordEncoder,
                       IRequestService requestService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
        this.passwordEncoder = passwordEncoder;
        this.requestService = requestService;
    }

    @Override
    @Transactional
    public User create(User user) {
        this.check(user);
        this.validate(user);

//        try {
//            User userFromDb = this.getByUsername(user.getUsername());
//            if (userFromDb == null) {
//                throw new EssenceExistException("User exists");
//            }
//        } catch (EssenceNotFoundException e) {
//            logger.info("User with username {} exists", user.getUsername());
//        }

        LocalDateTime now = LocalDateTime.now();
        user.setDtCreate(now);
        user.setDtUpdate(now);
        user.setRole(Role.USER);
        user.setEnabled(false);

        String verifyCode = UUID.randomUUID().toString();
        user.setVerifyCode(verifyCode);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity userEntity = this.conversionService.convert(user, UserEntity.class);
        if (userEntity != null) {
            userEntity = this.userRepository.save(userEntity);
            logger.info("In database was created user with username {}", userEntity.getUsername());
        }

        this.requestService.sendVerifyCode(user.getUsername(), user.getName(), verifyCode);

        return this.conversionService.convert(userEntity, User.class);
    }

    @Override
    public String login(Login login) {
        this.checkLogin(login);
        String username = login.getUsername();
        User userFromDB = this.getByUsername(username);

        if (!userFromDB.isEnabled()) {
            throw new UserDisableException("User is disable");
        }

        if (!passwordEncoder.matches(login.getPassword(), userFromDB.getPassword())) {
            throw new IncorrectInputParametersException("Wrong username or password");
        }
        return JwtTokenUtil.generateAccessToken(username);
    }

    @Override
    public User getByUsername(String username) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByUsername(username);
        if (optionalUserEntity.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        return this.conversionService.convert(optionalUserEntity.get(), User.class);
    }

    @Override
    @Transactional
    public User verify(String code) {
        Optional<UserEntity> optionalUserEntity = this.userRepository.findByVerifyCode(code);
        if (optionalUserEntity.isEmpty()) {
            throw new EssenceNotFoundException("Essence is not exist");
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setEnabled(true);
        userEntity.setVerifyCode(null);
        userEntity = this.userRepository.save(userEntity);
        return this.conversionService.convert(userEntity, User.class);
    }

    /**
     * Checking the user for the correctness of the mail and the complexity of the password
     *
     * @param user user dto
     */
    private void validate(User user) {
        List<ValidationError> errors = new ArrayList<>();

        Pattern emailPattern = Pattern.compile(".+@.+\\..+");
        Matcher emailMatcher = emailPattern.matcher(user.getUsername());

        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher passwordMatcher = passwordPattern.matcher(user.getPassword());

        if (!emailMatcher.matches()) {
            errors.add(new ValidationError("username", "The username must be email address"));
        }
        if (!passwordMatcher.matches()) {
            errors.add(new ValidationError("password", "The password must be difficult"));
        }
        if (!errors.isEmpty()) {
            throw new ValidationException("Registration's validation error", errors);
        }
    }

    /**
     * Checking the user for null and an empty string
     *
     * @param user user dto
     */
    private void check(User user) {
        List<ValidationError> errors = new ArrayList<>();
        this.checkString(user.getName(), "name", errors);
        this.checkString(user.getUsername(), "username", errors);
        this.checkString(user.getPassword(), "password", errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Registration's validation error", errors);
        }
    }

    /**
     * Checking the login for null and an empty string
     *
     * @param login login dto
     */
    private void checkLogin(Login login) {
        List<ValidationError> errors = new ArrayList<>();
        this.checkString(login.getUsername(), "username", errors);
        this.checkString(login.getPassword(), "password", errors);

        if (!errors.isEmpty()) {
            throw new ValidationException("Registration's validation error", errors);
        }
    }

    /**
     * Checking string for null and emptiness
     *
     * @param str    checking string
     * @param field  field in dto
     * @param errors errors list
     */
    private void checkString(String str, String field, List<ValidationError> errors) {
        if (str == null || str.isEmpty()) {
            String message = field + " is null or empty";
            errors.add(new ValidationError(field, message));
        }
    }

}
