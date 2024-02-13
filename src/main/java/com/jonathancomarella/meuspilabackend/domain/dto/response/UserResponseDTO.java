package com.jonathancomarella.meuspilabackend.domain.dto.response;

import com.jonathancomarella.meuspilabackend.domain.User;
import com.jonathancomarella.meuspilabackend.domain.UserRoleEnum;

public record UserResponseDTO(String email, String password, String firstname, String lastname, UserRoleEnum role){

    public UserResponseDTO(User user){
        this(user.getEmail(), user.getPassword(), user.getFirstname(), user.getLastname(), user.getRole());
    }
}
