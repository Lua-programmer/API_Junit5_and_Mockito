package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.services.exceptions.DataIntegrityViolationException;
import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import io.github.luaprogrammer.api.model.UserModel;
import io.github.luaprogrammer.api.model.dto.UserDto;
import io.github.luaprogrammer.api.repository.UserRepository;
import io.github.luaprogrammer.api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    @Override
    public UserDto create(UserDto user) {
        findByEmail(user);
        return mapper.map(
                repository.save(mapper.map(user, UserModel.class)),
                UserDto.class);
    }

    @Override
    public UserDto update(Integer id, UserDto user) {
        user.setId(id);
        findByEmail(user);
        return mapper.map(
                repository.save(mapper.map(user, UserModel.class)), UserDto.class);
    }

    @Override
    public void delete(Integer id) {
        UserModel user = repository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Objeto não encontrado")
        );
        repository.delete(user);
    }

    private void findByEmail(UserDto user) {
        Optional<UserModel> userSaved =  repository.findByEmail(user.getEmail());
        if (userSaved.isPresent() && !Objects.equals(userSaved.get().getId(), user.getId())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado no sistema");
        }
    }
}
