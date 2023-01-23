package io.github.luaprogrammer.api.config;

import io.github.luaprogrammer.api.model.UserModel;
import io.github.luaprogrammer.api.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    private final UserRepository repository;

    public LocalConfig(UserRepository repository) {
        this.repository = repository;
    }

    @Bean
    public void startDB() {
        UserModel u1 = new UserModel(null, "Zoe", "zoe@gmail.com", "123");
        UserModel u2 = new UserModel(null, "Petter", "petter@gmail.com", "123");

        repository.saveAll(List.of(u1, u2));
    }
}
