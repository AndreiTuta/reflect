/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andreituta.controller;

import com.andreituta.model.Meditation;
import com.andreituta.model.User;
import com.andreituta.model.repository.MeditationRepository;
import com.andreituta.model.repository.UserRepository;
import com.andreituta.model.security.SecurityUtil;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author at
 */
@Controller
public class MeditationController {
    @Autowired
    private MeditationRepository meditationRepository;
    // Constants
    private final String INVALID_CRED = "No param passed. Nothing has been executed";

    @RequestMapping(value = "/api/meditations", method = RequestMethod.POST)
    @ResponseBody
    public String addUsers(@RequestParam final String meditationName) {
        if (!StringUtils.isEmpty(meditationName)) {
            Meditation meditation = new Meditation();
            meditation.setName(meditationName);
            meditationRepository.save(meditation);
            return "Saved meditation " + meditation.getName();
        }
        return INVALID_CRED;

    }

//    @RequestMapping(value = "/api/user/{username}/{password}", method = RequestMethod.GET)
//    @ResponseBody
//    public String logUsers(@PathVariable(value = "username") final String username, @PathVariable(value = "password") final String password) {
//        if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
//            
//        }
//        return INVALID_CRED;
//    }
//
//    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public String udpateUsers(@PathVariable(value = "id") final String id) {
//        if (!StringUtils.isEmpty(id)) {
//           
//        }
//        return INVALID_CRED;
//    }
}
