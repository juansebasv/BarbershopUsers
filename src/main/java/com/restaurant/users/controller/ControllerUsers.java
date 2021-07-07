package com.restaurant.users.controller;

import com.restaurant.users.dto.UserDto;
import com.restaurant.users.dto.UserNewPasswordDto;
import com.restaurant.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/app")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST})
public class ControllerUsers {

    @Autowired
    private UserService userService;

    @PostMapping("/user/")
    public ResponseEntity<Object> insertClient(@RequestBody(required = true) UserDto user) {
        try {
            if (null != user) {
                userService.createUser(user);
                return new ResponseEntity<>("saved", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/")
    public ResponseEntity<List<UserDto>> getClients() {
        try {
            List<UserDto> users = userService.getUsers();
            if (null != users) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/")
    public ResponseEntity<String> updateClient(@RequestBody(required = true) UserDto user) {
        try {
            if (null != user) {
                userService.createUser(user);
                return new ResponseEntity<>("updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/")
    public ResponseEntity<String> deleteClient(int id) {
        try {
            if (id > 0) {
                userService.deleteUser(id);
                return new ResponseEntity<>("deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/changePassword/")
    public ResponseEntity<Object> changePassword(@RequestBody(required = true) UserNewPasswordDto userNewPAssword) {
        try {
            if (null != userNewPAssword) {
                userService.changePassword(userNewPAssword.getUser(), userNewPAssword.getNewPassword());
                return new ResponseEntity<>("password updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
