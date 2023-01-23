package io.github.luaprogrammer.api.services;

import io.github.luaprogrammer.api.model.dto.UserDto;

public interface UserService {
    UserDto findById(Integer id);
}
