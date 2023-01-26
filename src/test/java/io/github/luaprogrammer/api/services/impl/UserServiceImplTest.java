package io.github.luaprogrammer.api.services.impl;

import io.github.luaprogrammer.api.model.UserModel;
import io.github.luaprogrammer.api.model.dto.UserDto;
import io.github.luaprogrammer.api.services.exceptions.DataIntegrityViolationException;
import io.github.luaprogrammer.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceImplTest {

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final String E_MAIL_JA_CADASTRADO_NO_SISTEMA = "E-mail já cadastrado no sistema";
    @Autowired
    private UserServiceImpl service;
    private UserDto userDto;
    private UserModel user;

    @BeforeEach
    void setUp() {
        startUser();
    }

    @Test
    void whenFindByIdIdThenReturnUserInstance() {
        UserDto response = service.findById(1);
        assertEquals(UserDto.class, response.getClass());
    }

    @Test
    void whenFindByIdIdThenReturnExceptionIfUserNotFound() {
        try {
            service.findById(1);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {

        List<UserDto> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserDto.class, response.get(0).getClass());
    }

    @Test
    void whenCreateThenReturnSuccess() {

        UserDto response = service.create(userDto);
        assertNotNull(response);
        assertEquals(UserDto.class, response.getClass());
        assertEquals(3, response.getId());
        assertEquals("Zoe", response.getName());
        assertEquals("zoe@gmail.com", response.getEmail());
        assertEquals("123", response.getPassword());
    }

    @Test
    void whenCreateThenReturnDataIntegrityViolationException() {
        try {
            user.setId(2);
            service.create(userDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("E-mail já cadastrado no sistema", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        UserDto response = service.update(user.getId(), userDto);
        assertNotNull(response);
        assertEquals(UserDto.class, response.getClass());
        assertEquals(1, response.getId());
        assertEquals("Zoe", response.getName());
        assertEquals("zoe@gmail.com", response.getEmail());
        assertEquals("123", response.getPassword());
    }

    @Test
    void whenUpdateThenReturnDataIntegrityViolationException() {
        try {
            user.setId(2);
            service.create(userDto);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(E_MAIL_JA_CADASTRADO_NO_SISTEMA, ex.getMessage());
        }
    }

    @Test
    void deleteWithSuccess() {
        service.delete(1);

        List<UserDto> response = service.findAll();
        assertEquals(1, response.size());

    }

    @Test
    void deleteWithObjectNotFoundException() {
        try {
            service.delete(3);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    private void startUser() {
        userDto = new UserDto(1, "Zoe", "zoe@gmail.com", "123");
        user = new UserModel(1, "Zoe", "zoe@gmail.com", "123");
    }
}