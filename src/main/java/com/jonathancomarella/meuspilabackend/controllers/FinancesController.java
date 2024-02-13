package com.jonathancomarella.meuspilabackend.controllers;

import com.jonathancomarella.meuspilabackend.MeuspilaBackendApplication;
import com.jonathancomarella.meuspilabackend.domain.dto.request.FinancesRequestDTO;
import com.jonathancomarella.meuspilabackend.domain.dto.response.FinancesResponseDTO;
import com.jonathancomarella.meuspilabackend.services.FinancesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("finances")
@SecurityRequirement(name = "bearer-key")
public class FinancesController {

    private static Logger logger = LoggerFactory.getLogger(MeuspilaBackendApplication.class);

    @Autowired
    private FinancesService service;


    @PostMapping
    public ResponseEntity postCreateFinances(@RequestBody @Valid FinancesRequestDTO body){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FinancesResponseDTO newFinance = service.createFinance(body, authentication.getName());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(body.getId()).toUri();
        return ResponseEntity.created(uri).body(newFinance);
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        FinancesResponseDTO financesResponseDTO = service.findById(id);
        return ResponseEntity.ok(financesResponseDTO);
    }

    @GetMapping("/user")
    public ResponseEntity getAllFinancesByEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<FinancesResponseDTO> financesResponseDTOList = service.findAllFinancesByEmail(authentication.getName());
        return ResponseEntity.ok(financesResponseDTOList);
    }

    @GetMapping
    public ResponseEntity getAllFinances(){
        List<FinancesResponseDTO> financesResponseDTOList = service.findAllFinances();
        return ResponseEntity.ok(financesResponseDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        service.delete(id);
        logger.info(authentication.getName() + " - Deletou Finance: " + id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable String id, @Valid @RequestBody FinancesResponseDTO body) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        body = service.update(id, body);
        logger.info(authentication.getName() + " - Atualizou a Finance: " + id);
        return ResponseEntity.ok().body(body);
    }
}
