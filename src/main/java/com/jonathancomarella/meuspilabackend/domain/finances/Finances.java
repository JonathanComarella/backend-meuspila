package com.jonathancomarella.meuspilabackend.domain.finances;

import com.jonathancomarella.meuspilabackend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "finances")
public class Finances {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private BigDecimal amount;
    @Column(name = "typefinances")
    private String typeFinances;
    private boolean repeat;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User userEmail;


    public Finances(FinancesRequestDTO data) {
        this.description = data.description();
        this.amount = data.amount();
        this.typeFinances = data.typeFinances();
        this.repeat = data.repeat();
        this.active = data.active();
        this.userEmail = data.userEmail();
    }
}