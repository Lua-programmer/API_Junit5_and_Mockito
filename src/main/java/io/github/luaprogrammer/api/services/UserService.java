package io.github.luaprogrammer.api.services;

import io.github.luaprogrammer.api.model.User;

public interface UserService {
    User findById(Integer id);
}
