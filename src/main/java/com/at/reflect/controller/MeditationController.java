package com.at.reflect.controller;

import com.at.reflect.model.entity.Meditation;
import com.at.reflect.model.repository.MeditationRepository;
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
public class MeditationController {

    @Autowired
    private MeditationRepository meditationRepository;
    // Constants
    private final String INVALID_CRED = "No param passed. Nothing has been executed";
    private final String ENDPOINT = "/api/meditations";

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    @ResponseBody
    public String addMeditation(@RequestParam final String meditationName, @RequestParam final String meditationDuration, @RequestParam final boolean isAvailable) {
        if (!StringUtils.isEmpty(meditationName)) {
            Meditation meditation = new Meditation();
            meditation.setName(meditationName);
            meditation.setDuration(meditationDuration);
            meditation.setAvailable(isAvailable);
            meditationRepository.save(meditation);
            return "Saved meditation " + meditation.toString();
        }
        return INVALID_CRED;

    }

    @RequestMapping(value = ENDPOINT + "/{meditationName}", method = RequestMethod.GET)
    @ResponseBody
    public String fetchMeditation(@PathVariable(value = "meditationName") String meditationName) {
        if (!StringUtils.isEmpty(meditationName)) {
            Meditation meditation = StreamSupport.stream(meditationRepository.findAll().spliterator(), false)
                    .filter(med -> med.getName().equals(meditationName)).findAny().orElse(null);
            if (meditation != null) {
                return "Retrieved meditation: " + meditation.getName();
            } else {
                return "Failed returning a meditation for provided params.";
            }
        }
        return INVALID_CRED;
    }

    @RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String udpateMeditation(@PathVariable(value = "id") final String id, @RequestParam final String meditationName, @RequestParam final String meditationDuration, @RequestParam final boolean isAvailable) {
        if (!StringUtils.isEmpty(id)) {
            Meditation meditation = meditationRepository.findOne(Integer.valueOf(id));
            if (meditation != null) {
                log.debug("Updating meditation {}", meditation.getId());
                meditation.setName(meditationName);
                meditation.setDuration(meditationDuration);
                meditation.setAvailable(isAvailable);
                meditationRepository.save(meditation);
                return "Updated meditation: " + meditation.getName();
            } else {
                return "Failed returning a user for provided credentials.";
            }
        }
        return INVALID_CRED;
    }
}