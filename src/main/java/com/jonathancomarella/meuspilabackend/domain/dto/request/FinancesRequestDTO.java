package com.jonathancomarella.meuspilabackend.domain.dto.request;


import com.jonathancomarella.meuspilabackend.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class FinancesRequestDTO {

        private String id;
        @NotBlank
        private String description;
        @NotNull
        private BigDecimal amount;
        @NotBlank
        private String typeFinances;
        private boolean repeat;
        private boolean active;
        private User userEmail;
}
