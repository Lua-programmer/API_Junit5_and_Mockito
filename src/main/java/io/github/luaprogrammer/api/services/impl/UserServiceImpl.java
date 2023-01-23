package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.exceptions.ObjectNotFoundException;
import io.github.luaprogrammer.api.model.UserModel;
import io.github.luaprogrammer.api.repository.UserRepository;
import io.github.luaprogrammer.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserModel findById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }
}
