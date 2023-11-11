package com.jonathancomarella.meuspilabackend.controllers;

import com.jonathancomarella.meuspilabackend.domain.finances.Finances;
import com.jonathancomarella.meuspilabackend.domain.finances.FinancesRequestDTO;
import com.jonathancomarella.meuspilabackend.domain.finances.FinancesResponseDTO;
import com.jonathancomarella.meuspilabackend.domain.user.User;
import com.jonathancomarella.meuspilabackend.repositories.FinancesRepository;
import com.jonathancomarella.meuspilabackend.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("finances")
public class FinancesController {

    @Autowired
    FinancesRepository repository;
    @Autowired
    UserRepository userRepository;

    @PostMapping
    public ResponseEntity postCreateFinances(@RequestBody @Valid FinancesRequestDTO body){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // Obtém o email do usuario do contexto de segurança
        String email = authentication.getName();
        body.setUserEmail(userRepository.findUserByEmail(email));

        Finances newFinances = new Finances(body);

        this.repository.save(newFinances);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getAllFinances(){
        List<FinancesResponseDTO> financesResponseDTOList = this.repository.findAll().stream().map(FinancesResponseDTO::new).toList();

        return ResponseEntity.ok(financesResponseDTOList);
    }
}
