/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.andreituta.model.security;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author at
 */
@Component
public class SecurityUtil {

    public String encode(final String originalString) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
        String encodedPassword = encoder.encode(originalString);
        return encodedPassword;
    }

    public boolean decode(final String unencodedString, final String encodedString) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
        return encoder.matches(unencodedString, encodedString);
    }
}
