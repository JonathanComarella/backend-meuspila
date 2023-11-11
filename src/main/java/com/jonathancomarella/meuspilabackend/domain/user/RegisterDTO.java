package com.jonathancomarella.meuspilabackend.domain.user;

public record RegisterDTO(String email, String password, String firstname, String lastname, UserRole role) {

}
