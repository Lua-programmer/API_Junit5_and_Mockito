package io.github.luaprogrammer.api.services;

import io.github.luaprogrammer.api.model.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto findById(Integer id);
    List<UserDto> findAll();
    UserDto create(UserDto user);
}
