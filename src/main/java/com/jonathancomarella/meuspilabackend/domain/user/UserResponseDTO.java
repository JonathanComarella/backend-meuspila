package com.jonathancomarella.meuspilabackend.domain.user;

import com.jonathancomarella.meuspilabackend.domain.finances.Finances;

public record UserResponseDTO(String email, String password, String firstname, String lastname, UserRole role){

    public UserResponseDTO(User user){
        this(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getRole());
    }
}
