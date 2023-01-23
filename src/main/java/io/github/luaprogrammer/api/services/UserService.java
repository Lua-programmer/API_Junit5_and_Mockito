package io.github.luaprogrammer.api.services;

import io.github.luaprogrammer.api.model.UserModel;

public interface UserService {
    UserModel findById(Integer id);
}
