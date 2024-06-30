package com.alianza.AlianzaUsers.controllers;

import com.alianza.AlianzaUsers.dto.UsersDto;
import com.alianza.AlianzaUsers.entities.UsersEntity;
import com.alianza.AlianzaUsers.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsersControllerTest {

    @InjectMocks
    private UsersController usersController;

    @Mock
    private IUserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSave_Success() {
        UsersDto userDto = new UsersDto();
        UsersEntity userEntity = new UsersEntity();
        when(userService.save(any(UsersDto.class))).thenReturn(userEntity);

        ResponseEntity<?> response = usersController.save(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userEntity, response.getBody());
        verify(userService, times(1)).save(userDto);
    }

    @Test
    public void testSave_ResponseStatusException() {
        UsersDto userDto = new UsersDto();
        when(userService.save(any(UsersDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request"));

        ResponseEntity<?> response = usersController.save(userDto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody());
        verify(userService, times(1)).save(userDto);
    }

    @Test
    public void testFindBySharedKey_Success() {
        String sharedKey = "sharedKey123";
        UsersEntity userEntity = new UsersEntity();
        when(userService.findBySharedKey(sharedKey)).thenReturn(userEntity);

        ResponseEntity<?> response = usersController.findBySharedKey(sharedKey);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userEntity, response.getBody());
        verify(userService, times(1)).findBySharedKey(sharedKey);
    }

    @Test
    public void testFindBySharedKey_NotFound() {
        String sharedKey = "sharedKey123";
        when(userService.findBySharedKey(sharedKey)).thenReturn(null);

        ResponseEntity<?> response = usersController.findBySharedKey(sharedKey);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).findBySharedKey(sharedKey);
    }

    @Test
    public void testGetAllUsers_Success() {
        UsersEntity userEntity1 = new UsersEntity();
        UsersEntity userEntity2 = new UsersEntity();
        List<UsersEntity> usersList = Arrays.asList(userEntity1, userEntity2);
        when(userService.findAll()).thenReturn(usersList);

        ResponseEntity<?> response = usersController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usersList, response.getBody());
        verify(userService, times(1)).findAll();
    }

    @Test
    public void testGetAllUsers_NotFound() {
        when(userService.findAll()).thenReturn(null);

        ResponseEntity<?> response = usersController.getAllUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).findAll();
    }
}
