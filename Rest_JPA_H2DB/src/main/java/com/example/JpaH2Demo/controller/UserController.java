package com.example.JpaH2Demo.controller;

import com.example.JpaH2Demo.entity.User;
import com.example.JpaH2Demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<List<User>>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> findByName(@PathVariable("name") String name) {
        return new ResponseEntity<User>(userService.findByName(name), HttpStatus.OK);
    }

    @GetMapping(value = "passport/{passportNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findByPassportNumber(@PathVariable("passportNumber") String passportNumber) {
        return new ResponseEntity<List<User>>(userService.findByPassportNumber(passportNumber), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createStudent(@RequestBody User user, HttpServletResponse response) {
        return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateStudent(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(id);
    }
}
