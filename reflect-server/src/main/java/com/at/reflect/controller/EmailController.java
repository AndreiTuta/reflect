package com.at.reflect.controller;

import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.thymeleaf.util.StringUtils;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.at.reflect.model.email.repository.EmailRepository;
import com.at.reflect.model.entity.Email;

/**
 *
 * @author at
 */
@Controller
public class EmailController
{
    @Autowired
    private EmailRepository emailRepository;
    // Constants
    private final String INVALID_CRED = "No param passed. Nothing has been executed";
    private final String ENDPOINT = "/api/emails/";

    @RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
    @ResponseBody
    public String addEmail(@RequestParam final String emailBody,
            @RequestParam final String emailType,
            @RequestParam final boolean isTemplate)
    {
        if (!StringUtils.isEmpty(emailBody))
        {
            Email email = updateEmail(0, emailBody, emailType, isTemplate);
            return "Saved email " + email.toString();
        }
        return INVALID_CRED;

    }

    @RequestMapping(value = ENDPOINT + "{emailType}", method = RequestMethod.GET)
    @ResponseBody
    public String fetchEmail(@PathVariable(value = "emailType") String emailType)
    {
        if (!StringUtils.isEmpty(emailType))
        {
            Email email = StreamSupport.stream(emailRepository.findAll().spliterator(), false)
                    .filter(em -> em.getEmailType().equals(emailType)).findAny().orElse(null);
            if (email != null)
            {
                return "Retrieved email: " + email.toString();
            } else
            {
                return "Failed returning a email for provided params.";
            }
        }
        return INVALID_CRED;
    }

    @RequestMapping(value = ENDPOINT + "{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String udpateEmail(@PathVariable(value = "id") final String id,
            @RequestParam final String emailBody,
            @RequestParam final String emailType,
            @RequestParam final boolean isTemplate)
    {
        if (!StringUtils.isEmpty(id))
        {
			Email email = emailRepository.findById(Integer.valueOf(id)).orElse(null);
            if (email != null)
            {
                Email updatedEmail = updateEmail(email.getId(), emailBody, emailType, isTemplate);
                return "Updated email: " + updatedEmail.toString();
            } else
            {
                return "Failed returning a user for provided credentials.";
            }
        }
        return INVALID_CRED;
    }

    private Email updateEmail(Integer id, String emailBody, String emailType,
            boolean template)
    {
        Email email = new Email();
        if (id > 0)
        {
            email.setId(id);
        }
        email.setEmailBody(HtmlUtils.htmlEscape(emailBody));
        email.setEmailType(emailType);
        email.setTemplate(template);
        emailRepository.save(email);
        return email;
    }
}
