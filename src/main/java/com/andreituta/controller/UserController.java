package com.andreituta.controller;

import com.andreituta.model.User;
import com.andreituta.model.email.EmailUtil;
import com.andreituta.model.repository.UserRepository;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailUtil emailUtil;
    // Constants
    private final String INVALID_CRED = "No params passed. Nothing has been executed";
    private final String ENDPOINT = "/api/user";

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    @ResponseBody
    public String addUsers(@RequestParam final String username, @RequestParam final String password, @RequestParam final String emailAddress) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            User user = fetchUser(username, password, "");
            if (user != null) {
                emailUtil.sendEmail(emailAddress);
            } else {
                User newUser = new User();
                newUser.setName(username);
                newUser.setPassword(password);
                newUser.setEmail(emailAddress);
                userRepository.save(newUser);
                emailUtil.sendEmail(emailAddress);
                return "Adding username: " + username + " password: " + password;
            }
        }
        return INVALID_CRED;

    }

    @RequestMapping(value = ENDPOINT + "/{username}/{password}", method = RequestMethod.GET)
    @ResponseBody
    public String logUsers(@PathVariable(value = "username")
            final String username,
            @PathVariable(value = "password")
            final String password
    ) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            User user = fetchUser(username, password, "");
            if (user != null) {
                return "Retrieved username: " + user.getName() + " password: " + user.getPassword();
            } else {
                return "Failed returning a user for provided credentials.";
            }
        }
        return INVALID_CRED;
    }

    @RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String udpateUsers(@PathVariable(value = "id")
            final String id
    ) {
        if (!StringUtils.isEmpty(id)) {
            User user = fetchUser("", "", id);
            if (user != null) {
                return "Retrieved username: " + user.getName() + " password: " + user.getPassword();
            } else {
                return "Failed returning a user for provided credentials.";
            }
        }
        return INVALID_CRED;
    }

    private User fetchUser(String username, String password, String id) {
        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
            return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                    .filter(u -> username.equals(u.getName()) && password.equals(u.getPassword())).findAny().orElse(null);
        } else if (!StringUtils.isEmpty(id)) {
            return userRepository.findOne(Integer.valueOf(id));
        }
        return null;
    }
}
