package com.jonathancomarella.meuspilabackend.domain.user;

public record UserResponseDTO(String email, String password, String firstname, String lastname, UserRole role){
}
