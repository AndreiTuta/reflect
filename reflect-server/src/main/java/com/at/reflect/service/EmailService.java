package com.at.reflect.service;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.at.reflect.dao.EmailDao;
import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.error.exception.PathException;
import com.at.reflect.model.request.EmailRequest;
import com.at.reflect.model.response.EmailResponse;
import com.reflect.generated.tables.pojos.Email;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailService implements Service {
    private final EmailDao emailDao;
    private final ModelMapper modelMapper;

    private EmailResponse.EmailResponseBuilder buildEmailResponse(final Email email) {
        return EmailResponse.builder()
                           .id(email.getId())
                           .available(email.getAvailable())
                           .text(email.getText())
                           .name(email.getName())
                           .address(email.getAddress())
                           .preview(email.getPreview());
    }

    @Override
    public ServiceType getType() {
        return ServiceType.USER;
    }

    public EmailResponse createEmail(@Valid EmailRequest emailRequest) {
        final Email email = modelMapper.map(emailRequest, Email.class);
        emailDao.insert(email);
        return buildEmailResponse(email).build();
    }

    public void updateEmail(String emailId, @Valid EmailRequest emailRequest) {
        // TODO Auto-generated method stub

    }

    public EmailResponse fetchEmailById(String emailId) throws NotFoundException {
        try {
            int id = Integer.parseInt(emailId);
            final Email email = fetchEmailById(id)
                                               .orElseThrow(() -> new NotFoundException("Email with id: " + emailId + " not found"));
            return buildEmailResponse(email).build();
        } catch (NumberFormatException e) {
            throw new PathException("meditationId on path must be an integer");
        }
    }

    public Optional<Email> fetchEmailById(final Integer id) {
        return Optional.ofNullable(emailDao.findById(id));

    }

    public EmailResponse delete(String emailId) {
        return new EmailResponse();
    }
}
