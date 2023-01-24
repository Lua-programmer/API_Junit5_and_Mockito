package io.github.luaprogrammer.api.resources;

import io.github.luaprogrammer.api.model.dto.UserDto;
import io.github.luaprogrammer.api.services.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserResource {
    public static final String ID = "/{id}";
    private final UserServiceImpl service;

    @GetMapping(ID)
    public ResponseEntity<UserDto> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto user) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(service.create(user).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(ID)
    public ResponseEntity<UserDto> update(@PathVariable Integer id, @RequestBody UserDto user) {
        service.update(id, user);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(ID)

    public ResponseEntity<UserDto> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
