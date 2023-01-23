package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.exceptions.ObjectNotFoundException;
import io.github.luaprogrammer.api.model.dto.UserDto;
import io.github.luaprogrammer.api.repository.UserRepository;
import io.github.luaprogrammer.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper mapper;

    private final UserRepository repository;

    @Override
    public UserDto findById(Integer id) {
        return mapper.map(
                repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado")),
                UserDto.class
        );
    }

    @Override
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(u -> mapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }
}
