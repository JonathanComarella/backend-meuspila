package com.jonathancomarella.meuspilabackend.domain.dto.request;


import com.jonathancomarella.meuspilabackend.domain.UserRoleEnum;

public record RegisterDTO(String email, String password, String firstname, String lastname, UserRoleEnum role) {

}
