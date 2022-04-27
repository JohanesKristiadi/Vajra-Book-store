package com.haibcaminiproject.springboot.services;

import com.haibcaminiproject.springboot.models.User;
import com.haibcaminiproject.springboot.models.UserDetail;
import com.haibcaminiproject.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService implements UserDAOService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("User does not exists"));
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User does not exists"));
    }

    public void addNewUser(User user) {
        Optional<User> newUser = userRepository.findByEmail(user.getEmail());

        if (newUser.isPresent()) {
            throw new IllegalStateException("Email taken");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreatedAt(Instant.now());
        user.setUpdatedAt(Instant.now());
        userRepository.save(user);
    }

    @Transactional
    public void updateUser(Long userId, String name, String email) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User does not exists"));

        if (name != null && name.length() > 0 && !Objects.equals(user.getName(), name)) {
            user.setName(name);
            user.setUpdatedAt(Instant.now());
        }

        if (email != null && email.length() > 0 && !Objects.equals(user.getEmail(), email)) {
            Optional<User> checkEmail = userRepository.findByEmail(email);

            if (checkEmail.isPresent()) {
                throw new IllegalStateException("Email taken");
            }
            user.setEmail(email);
            user.setUpdatedAt(Instant.now());
        }
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalStateException("User does not exists");
        }
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return new UserDetail(user);
    }
}
