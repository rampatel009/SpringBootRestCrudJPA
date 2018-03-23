package com.example.JpaH2Demo.service;

import com.example.JpaH2Demo.entity.User;
import com.example.JpaH2Demo.exception.UserException;
import com.example.JpaH2Demo.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User entity) {
        return userRepository.save(entity);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Transactional(readOnly = true)
    public List<User> findByPassportNumber(String name) {
        List<User> stdList = null;
        try (Stream<User> studentStream = userRepository.findByPassportNumberReturnStream(name)) {
            stdList = studentStream.collect(Collectors.toList());
        } catch(Exception ex){
            throw new UserException("Error occurred while searching user >> 'name' : " + name);
        }
        return stdList;
    }

    public void update(User entity) {
        if(userRepository.existsById(entity.getId())) {
            userRepository.save(entity);
        } else {
            throw new UserException("User with 'id' doesn't exists: " + entity.getId());
        }
    }

    public void deleteById(long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserException("User 'id' doesn't exists: " + id);
        }
    }

    public void deleteByName(String name) {
        User user = this.findByName(name);
        if(user != null) {
            userRepository.delete(user);
        }
    }
}
