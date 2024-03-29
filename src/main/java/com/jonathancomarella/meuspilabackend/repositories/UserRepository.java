package com.jonathancomarella.meuspilabackend.repositories;


import com.jonathancomarella.meuspilabackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByEmail(String email);

    User findUserByEmail(String email);

    void deleteByEmail(String email);
}
