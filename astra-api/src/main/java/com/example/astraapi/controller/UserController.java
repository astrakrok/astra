package com.example.astraapi.controller;

import com.example.astraapi.dto.UpdateUserDto;
import com.example.astraapi.dto.UserDto;
import com.example.astraapi.meta.Endpoint;
import com.example.astraapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(Endpoint.USERS)
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/current")
  public UserDto getCurrentUser() {
    return userService.getCurrentUser();
  }

  @PutMapping
  public void update(@Valid @RequestBody UpdateUserDto user) {
    userService.update(user);
  }
}
