package com.jonathancomarella.meuspilabackend.repositories;

import com.jonathancomarella.meuspilabackend.domain.Finances;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinancesRepository extends JpaRepository<Finances, String> {
    List<Finances> findDistinctByUserEmail_Email(String email);
}
