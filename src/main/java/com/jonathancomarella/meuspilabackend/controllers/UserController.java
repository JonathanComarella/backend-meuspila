package com.jonathancomarella.meuspilabackend.controllers;

import com.jonathancomarella.meuspilabackend.domain.user.RegisterDTO;
import com.jonathancomarella.meuspilabackend.domain.user.UserResponseDTO;
import com.jonathancomarella.meuspilabackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO body){
        if (userService.createUser(body)) return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {
        List<UserResponseDTO> userResponseDTOList = userService.getAll();
        return ResponseEntity.ok().body(userResponseDTOList);
    }

    @GetMapping
    public ResponseEntity getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserResponseDTO userResponseDTO = userService.findUserByEmail(authentication.getName());
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody UserResponseDTO body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        body = userService.update(authentication.getName(), body);
        return ResponseEntity.ok().body(body);
    }

    @DeleteMapping
    public ResponseEntity delete () {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteById(authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
