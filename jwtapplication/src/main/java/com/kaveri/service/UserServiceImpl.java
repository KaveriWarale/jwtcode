package com.kaveri.service;

import com.kaveri.model.User;
import com.kaveri.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    public User saveData(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(int userId) {
        return userRepository.findById(userId);
    }

    public User updateData(User user) {
        return userRepository.save(user);
    }

    public void deleteById(int userId) {
        userRepository.deleteById(userId);
    }

    public void deleteAll() {
        userRepository.deleteAll();
    }

    public List<User> saveBulkOfData(List<User> user) {
        return userRepository.saveAll(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), new ArrayList<>());
    }
}
