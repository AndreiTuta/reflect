package com.andreituta.controller;

import com.andreituta.model.User;
import com.andreituta.model.repository.UserRepository;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    private final String INVALID_CRED = "No username/ password passed. Nothing has been executed";

    @RequestMapping(value = "/api/user", method = RequestMethod.POST)
    @ResponseBody
    public String addUsers(@RequestParam final String username, @RequestParam final String password) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            User user = new User();
            user.setName(username);
            user.setPassword(password);
            userRepository.save(user);
            return "Adding username: " + username + " password: " + password;
        }
        return INVALID_CRED;

    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    @ResponseBody
    public String logUsers(@RequestParam final String username, @RequestParam final String password) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            User user = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                    .filter(u -> u.getName().equals(username) && u.getPassword().equals(password)).findAny().orElse(null);
            if (user != null) {
                return "Retrieved username: " + user.getName() + " password: " + user.getPassword();
            } else {
                return "Failed returning a user for provided credentials.";
            }
        }
        return INVALID_CRED;
    }
}
