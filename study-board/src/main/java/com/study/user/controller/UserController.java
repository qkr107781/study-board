package com.study.user.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.user.domain.UserDto;
import com.study.user.domain.UserEntity;
import com.study.user.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public UserEntity addUser(@RequestBody @Valid UserDto user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
        	String usernameError = "";
        	String passwordError = "";
            for (FieldError error : bindingResult.getFieldErrors()) {
            	if("username".equals(error.getField())) {
            		usernameError = error.getDefaultMessage();
            	}
            	if("password".equals(error.getField())) {
            		passwordError = error.getDefaultMessage();
            	}
            }
            return UserEntity.builder().username(usernameError).password(passwordError).build();
        }
    	return userService.addUser(user);
    }
}
