package com.at.reflect.controller;

import com.at.reflect.model.entities.Email;
import com.at.reflect.model.email.repository.EmailRepository;
import org.springframework.web.util.HtmlUtils;
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
public class EmailController
{

    @Autowired
    private EmailRepository emailRepository;
    // Constants
    private final String INVALID_CRED = "No param passed. Nothing has been executed";
    private final String ENDPOINT = "/api/emails";

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    @ResponseBody
    public String addEmail(@RequestParam final String emailBody,
            @RequestParam final String emailType,
            @RequestParam final boolean isTemplate)
    {
        if (!StringUtils.isEmpty(emailBody))
        {
            Email email = new Email();
            email.setEmailBody(HtmlUtils.htmlEscape(emailBody));
            email.setEmailType(emailType);
            email.setTemplate(isTemplate);
            emailRepository.save(email);
            return "Saved email " + email.toString();
        }
        return INVALID_CRED;

    }

    @RequestMapping(value = ENDPOINT + "/{emailType}", method = RequestMethod.GET)
    @ResponseBody
    public String fetchEmail(@PathVariable(value = "emailType") String emailType)
    {
        if (!StringUtils.isEmpty(emailType))
        {
            Email email = StreamSupport.stream(emailRepository.findAll().spliterator(), false)
                    .filter(med -> med.getEmailType().equals(emailType)).findAny().orElse(null);
            if (email != null)
            {
                return "Retrieved email: " + email.getEmailType();
            } else
            {
                return "Failed returning a email for provided params.";
            }
        }
        return INVALID_CRED;
    }

    @RequestMapping(value = ENDPOINT + "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String udpateEmail(@PathVariable(value = "id") final String id,
            @RequestParam final String emailName,
            @RequestParam final String emailDuration,
            @RequestParam final boolean isAvailable)
    {
        if (!StringUtils.isEmpty(id))
        {
            Email email = emailRepository.findOne(Integer.valueOf(id));
            if (email != null)
            {
                emailRepository.save(email);
                return "Updated email: " + email.getEmailType();
            } else
            {
                return "Failed returning a user for provided credentials.";
            }
        }
        return INVALID_CRED;
    }
}
