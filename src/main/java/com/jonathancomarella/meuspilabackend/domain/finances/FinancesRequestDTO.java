package com.jonathancomarella.meuspilabackend.domain.finances;


import com.jonathancomarella.meuspilabackend.domain.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record FinancesRequestDTO(
        @NotBlank
        String description,
        @NotNull
        BigDecimal amount,
        @NotBlank
        String typeFinances,
        boolean repeat,
        boolean active,
        User userEmail
) {
}
