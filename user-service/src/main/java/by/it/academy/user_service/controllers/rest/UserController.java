package by.it.academy.user_service.controllers.rest;

import by.it.academy.user_service.models.dto.Login;
import by.it.academy.user_service.models.dto.User;
import by.it.academy.user_service.services.api.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<User> registration(@RequestBody User user) {
        return new ResponseEntity<>(this.userService.create(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Login login) {
        return new ResponseEntity<>(this.userService.login(login), HttpStatus.OK);
    }

    @RequestMapping(value = "/verify/{code}", method = RequestMethod.GET)
    public ResponseEntity<String> verify(@PathVariable String code) {
        this.userService.verify(code);
        String message = "Registration was successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResponseEntity<User> get(@PathVariable String username) {
        return new ResponseEntity<>(this.userService.getByUsername(username), HttpStatus.OK);
    }

}
