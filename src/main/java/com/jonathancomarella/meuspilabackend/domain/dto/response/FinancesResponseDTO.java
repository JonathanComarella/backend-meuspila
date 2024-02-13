package com.jonathancomarella.meuspilabackend.domain.dto.response;

import com.jonathancomarella.meuspilabackend.domain.Finances;
import java.math.BigDecimal;

public record FinancesResponseDTO(String id, String description, BigDecimal amount, String typeFinances, boolean repeat, boolean active) {

    public FinancesResponseDTO(Finances finances){
        this(finances.getId(), finances.getDescription(), finances.getAmount(), finances.getTypeFinances(), finances.isRepeat(), finances.isActive());
    }
}
