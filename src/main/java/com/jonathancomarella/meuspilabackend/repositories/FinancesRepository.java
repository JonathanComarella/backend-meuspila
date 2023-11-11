package com.jonathancomarella.meuspilabackend.repositories;

import com.jonathancomarella.meuspilabackend.domain.finances.Finances;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancesRepository extends JpaRepository<Finances, String> {
}
