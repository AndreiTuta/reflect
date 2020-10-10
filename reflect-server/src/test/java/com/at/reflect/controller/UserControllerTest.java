/**
 * 
 */
package com.at.reflect.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.at.reflect.error.exception.NotFoundException;
import com.at.reflect.model.request.UserRequest;
import com.at.reflect.model.response.UserResponse;
import com.at.reflect.service.UserService;

/**
 * @author Andrei Tuta
 *
 */
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    @Mock
    private UserResponse responseMock;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    /**
     * Test method for
     * {@link com.at.reflect.controller.UserController#addUser(com.at.reflect.model.request.UserRequest)}.
     */
    @Test
    void testAddUser() {
        when(userService.createUser(any(UserRequest.class))).thenReturn(responseMock);
        @Valid
        UserRequest userRequest = new UserRequest(LocalDateTime.now().toString(), "test-email@gmail.com",
                                                  LocalDateTime.now().plusHours(1).toString(), "test-name", "test-password");
        ResponseEntity<UserResponse> response = userController.addUser(userRequest);
        assertTrue(response.getStatusCodeValue() == 201);
        verify(userService, times(1)).createUser(any(UserRequest.class));
    }

    /**
     * Test method for
     * {@link com.at.reflect.controller.UserController#fetchUser(java.lang.String)}.
     * 
     * @throws NotFoundException
     */
    @Test
    void testFetchUser() throws NotFoundException {
        when(userService.fetchUserResponseById(anyString())).thenReturn(responseMock);
        ResponseEntity<UserResponse> response = userController.fetchUser("1");
        assertTrue(response.getStatusCodeValue() == 200);
        verify(userService, times(1)).fetchUserResponseById(anyString());
    }

    /**
     * Test method for
     * {@link com.at.reflect.controller.UserController#udpateUser(java.lang.String, com.at.reflect.model.request.UserRequest)}.
     * 
     * @throws NotFoundException
     */
    @Test
    void testUdpateUser() throws NotFoundException {
        when(userService.updateUser(anyString(), any(UserRequest.class))).thenReturn(responseMock);
        @Valid
        UserRequest userRequest = mock(UserRequest.class);
        userController.udpateUser("1", userRequest);
        verify(userService, times(1)).updateUser(anyString(), any(UserRequest.class));
    }

}
