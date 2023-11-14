package com.jonathancomarella.meuspilabackend.services;

import com.jonathancomarella.meuspilabackend.domain.user.RegisterDTO;
import com.jonathancomarella.meuspilabackend.domain.user.User;
import com.jonathancomarella.meuspilabackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean createUser(RegisterDTO body) {
        if (findByEmail(body.email()) != null) return false;

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        User newUser = new User(body.email(), encryptedPassword, body.firstname(), body.lastname(), body.role());
        newUser = userRepository.save(newUser);
        System.out.println(newUser);
        return true;
    }
}
