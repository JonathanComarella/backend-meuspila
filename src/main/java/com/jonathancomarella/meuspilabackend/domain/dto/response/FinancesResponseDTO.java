package com.jonathancomarella.meuspilabackend.domain.dto.response;

import com.jonathancomarella.meuspilabackend.domain.Finances;
import com.jonathancomarella.meuspilabackend.domain.StatusFinance;

import java.math.BigDecimal;

public record FinancesResponseDTO(String id, String description, BigDecimal amount, String typeFinances, StatusFinance status, boolean repeat, boolean active) {

    public FinancesResponseDTO(Finances finances){
        this(finances.getId(), finances.getDescription(), finances.getAmount(), finances.getTypeFinances(), finances.getStatus(), finances.isRepeat(), finances.isActive());
    }
}
