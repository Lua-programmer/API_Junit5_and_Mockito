package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.model.UserModel;
import io.github.luaprogrammer.api.model.dto.UserDto;
import io.github.luaprogrammer.api.repository.UserRepository;
import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

@SuppressWarnings("AssertBetweenInconvertibleTypes")
@SpringBootTest
class UserServiceImplTest {

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    @Autowired
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Mock
    private ModelMapper mapper;
    private UserDto userDto;
    private UserModel user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdIdThenReturnUserInstance() {
        when(repository.findById(anyInt())).thenReturn(
                Optional.ofNullable(user));

        UserDto response = service.findById(1);
        assertEquals(UserDto.class, response.getClass());
    }

    @Test
    void whenFindByIdIdThenReturnExceptionIfUserNotFound() {
        when(repository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try {
            service.findById(1);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(user));

        List<UserDto> response = service.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserDto.class, response.get(0).getClass());
    }

    @Test
    void whenCreateThenReturnSuccess() {
        when(repository.save(any())).thenReturn(user);

        UserDto response = service.create(userDto);
        assertNotNull(response);
        assertEquals(UserDto.class, response.getClass());
        assertEquals(1, response.getId());
        assertEquals("Zoe", response.getName());
        assertEquals("zoe@gmail.com", response.getEmail());
        assertEquals("123", response.getPassword());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser() {
        userDto = new UserDto(1, "Zoe", "zoe@gmail.com", "123");
        user = new UserModel(1, "Zoe", "zoe@gmail.com", "123");
    }
}