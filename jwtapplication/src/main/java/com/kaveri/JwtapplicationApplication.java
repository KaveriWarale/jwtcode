package com.kaveri;

import com.kaveri.model.User;
import com.kaveri.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class JwtapplicationApplication {

    @Autowired
    private UserRepository userRepositoryImpl;

    public static void main(String[] args) {
        SpringApplication.run(JwtapplicationApplication.class, args);
    }

    @PostConstruct
    public void saveUserData() {
        userRepositoryImpl.save(new User(121, "kaveri@gmail.com", "kaveri"));
    }
}
