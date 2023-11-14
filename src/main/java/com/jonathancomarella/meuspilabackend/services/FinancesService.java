package com.jonathancomarella.meuspilabackend.services;

import com.jonathancomarella.meuspilabackend.domain.finances.Finances;
import com.jonathancomarella.meuspilabackend.domain.finances.FinancesRequestDTO;
import com.jonathancomarella.meuspilabackend.domain.finances.FinancesResponseDTO;
import com.jonathancomarella.meuspilabackend.repositories.FinancesRepository;
import com.jonathancomarella.meuspilabackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FinancesService {

    @Autowired
    private FinancesRepository financesRepository;
    @Autowired
    private UserRepository userRepository;

    public List<FinancesResponseDTO> findAllFinancesByEmail(String email) {
        List<FinancesResponseDTO> financesResponseDTOList = financesRepository.findDistinctByUserEmail_Email(email).stream().map(FinancesResponseDTO::new).toList();
        return financesResponseDTOList;
    }

    public List<FinancesResponseDTO> findAllFinances() {
        List<FinancesResponseDTO> financesResponseDTOList = financesRepository.findAll().stream().map(FinancesResponseDTO::new).toList();
        return financesResponseDTOList;
    }

    public FinancesResponseDTO createFinance(FinancesRequestDTO body, String email) {
        body.setUserEmail(userRepository.findUserByEmail(email));
        Finances newFinances = new Finances(body);
        newFinances = financesRepository.save(newFinances);
        return new FinancesResponseDTO(newFinances);
    }

    public FinancesResponseDTO findById(String id) {
        try {
            Optional<Finances> finances = financesRepository.findById(id);
            if (finances.isEmpty()) throw new NoSuchElementException("Finances not found with id: " + id);
            else {
                return new FinancesResponseDTO(finances.get());
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
