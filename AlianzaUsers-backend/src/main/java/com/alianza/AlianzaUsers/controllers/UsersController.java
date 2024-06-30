package com.alianza.AlianzaUsers.controllers;

import com.alianza.AlianzaUsers.dto.UsersDto;
import com.alianza.AlianzaUsers.entities.UsersEntity;
import com.alianza.AlianzaUsers.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsersController {

    @Autowired
    private IUserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody UsersDto user) {
       try {
           UsersEntity saveUser = userService.save(user);
           return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
       } catch (ResponseStatusException e) {
           return new ResponseEntity<>(e.getReason(), e.getStatusCode());
       }
    }

    @GetMapping("/bySharedKey")
    public ResponseEntity<?> findBySharedKey(@RequestParam("sharedKey") String sharedKey) {
        UsersEntity bySharedKey = userService.findBySharedKey(sharedKey);
        if (bySharedKey != null) {
            return new ResponseEntity<>(bySharedKey, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers() {
        List<UsersEntity> allUsers = userService.findAll();
        if (allUsers != null) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
