package by.it.academy.account_scheduler_service.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    public UserDetails getUser(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
