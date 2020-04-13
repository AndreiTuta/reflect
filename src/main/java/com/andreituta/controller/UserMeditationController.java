/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andreituta.controller;

import com.andreituta.model.Meditation;
import com.andreituta.model.User;
import com.andreituta.model.UserMeditation;
import com.andreituta.model.repository.MeditationRepository;
import com.andreituta.model.repository.UserMeditationRepository;
import com.andreituta.model.repository.UserRepository;
import com.andreituta.model.security.SecurityUtil;
import java.util.stream.StreamSupport;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserMeditationController {

    @Autowired
    private MeditationRepository meditationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMeditationRepository userMeditationRepository;
    // Constants
    private final String INVALID_CRED = "No param passed. Nothing has been executed";

//    @RequestMapping(value = "/api/meditations", method = RequestMethod.POST)
//    @ResponseBody
//    public String addMeditation(@RequestParam final String meditationName, @RequestParam final String meditationDuration, @RequestParam final boolean isAvailable) {
//        if (!StringUtils.isEmpty(meditationName)) {
//            Meditation meditation = new Meditation();
//            meditation.setName(meditationName);
//            meditation.setDuration(meditationDuration);
//            meditation.setAvailable(isAvailable);
//            meditationRepository.save(meditation);
//            return "Saved meditation " + meditation.toString();
//        }
//        return INVALID_CRED;
//        
//    }
//    
//    @RequestMapping(value = "/api/meditations/{meditationName}", method = RequestMethod.GET)
//    @ResponseBody
//    public String fetchMeditation(@PathVariable(value = "meditationName") String meditationName) {
//        if (!StringUtils.isEmpty(meditationName)) {
//            Meditation meditation = StreamSupport.stream(meditationRepository.findAll().spliterator(), false)
//                    .filter(med -> med.getName().equals(meditationName)).findAny().orElse(null);
//            if (meditation != null) {
//                return "Retrieved meditation: " + meditation.getName();
//            } else {
//                return "Failed returning a meditation for provided params.";
//            }
//        }
//        return INVALID_CRED;
//    }
    @RequestMapping(value = "/api/meditations/{meditationId}/{userId}", method = RequestMethod.POST)
    @ResponseBody
    public String udpateMeditation(@PathVariable(value = "meditationId") final String meditationId, @PathVariable(value = "userId") final String userId) {
        if (!StringUtils.isEmpty(meditationId) && !StringUtils.isEmpty(userId)) {
            Meditation meditation = meditationRepository.findOne(Integer.valueOf(meditationId));
            User user = userRepository.findOne(Integer.valueOf(userId));
            if (meditation != null && user != null) {
                log.debug("Adding meditation {} for user", meditation.getId(), userId);
                UserMeditation userMeditation = new UserMeditation();
                userMeditation.setMeditationId(meditation.getId());
                userMeditation.setUserId(user.getId());
                userMeditationRepository.save(userMeditation);
                return "Updated meditation: " + meditation.getName();
            } else {
                return "Failed returning a user for provided credentials.";
            }
        }
        return INVALID_CRED;
    }
}
