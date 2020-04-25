package com.at.reflect.controller;

import com.at.reflect.model.entities.Meditation;
import com.at.reflect.model.entities.User;
import com.at.reflect.model.entities.UserMeditation;
import com.at.reflect.model.repository.MeditationRepository;
import com.at.reflect.model.repository.UserMeditationRepository;
import com.at.reflect.model.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
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
    private final String ENDPOINT = "/api/meditations/users";

    @RequestMapping(value = ENDPOINT + "/{userId}/meditations/{meditationId}", method = RequestMethod.POST)
    @ResponseBody
    public String addMeditation(@PathVariable(value = "meditationId") final String meditationId, @PathVariable(value = "userId") final String userId) {
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
                return "Failed returning a user/ meditation for provided credentials.";
            }
        }
        return INVALID_CRED;
    }

    @RequestMapping(value = ENDPOINT + "/{userId}/meditations", method = RequestMethod.GET)
    @ResponseBody
    public String fetchUserMeditations(@PathVariable(value = "userId") String userId, @RequestParam boolean selectAvailable) {
        if (!StringUtils.isEmpty(userId)) {
            User user = userRepository.findOne(Integer.valueOf(userId));
            if (user != null) {
                if (selectAvailable) {
                    List<Meditation> availableMeditations = StreamSupport.stream(meditationRepository.findAll().spliterator(), false)
                            .filter(med -> med.isAvailable()).collect(Collectors.toList());
                    return "Available: " + availableMeditations.size();
                } else {
                    List<UserMeditation> userMeditations = StreamSupport.stream(userMeditationRepository.findAll().spliterator(), false)
                            .filter(med -> user.getId() == med.getUserId()).collect(Collectors.toList());
                    return "Done: " + userMeditations.size();
                }
            } else {
                return "Failed returning a user for provided params.";
            }
        }
        return INVALID_CRED;
    }

    @RequestMapping(value = ENDPOINT + "/{userId}/meditations/{meditationId}", method = RequestMethod.GET)
    @ResponseBody
    public String fetchUserSpecificMeditation(@PathVariable(value = "meditationId") final String meditationId, @PathVariable(value = "userId") final String userId) {
        if (!StringUtils.isEmpty(meditationId) && !StringUtils.isEmpty(userId)) {
            UserMeditation userMeditation = StreamSupport.stream(userMeditationRepository.findAll().spliterator(), false)
                    .filter(userMed -> userMed.getId() == Integer.valueOf(meditationId) && userMed.getUserId() == Integer.valueOf(userId)).findAny().orElse(null);
            if (userMeditation != null) {
                return "Retrieved meditation: " + userMeditation.toString();
            } else {
                return "Failed returning a user/ meditation for provided credentials.";
            }
        }
        return INVALID_CRED;
    }
}