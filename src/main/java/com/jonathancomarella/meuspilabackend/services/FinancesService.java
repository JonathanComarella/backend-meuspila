package com.jonathancomarella.meuspilabackend.services;

import com.jonathancomarella.meuspilabackend.domain.finances.Finances;
import com.jonathancomarella.meuspilabackend.domain.finances.FinancesRequestDTO;
import com.jonathancomarella.meuspilabackend.domain.finances.FinancesResponseDTO;
import com.jonathancomarella.meuspilabackend.repositories.FinancesRepository;
import com.jonathancomarella.meuspilabackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FinancesService {

    @Autowired
    private FinancesRepository financesRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<FinancesResponseDTO> findAllFinancesByEmail(String email) {
        List<FinancesResponseDTO> financesResponseDTOList = financesRepository.findDistinctByUserEmail_Email(email).stream().map(FinancesResponseDTO::new).toList();
        return financesResponseDTOList;
    }

    @Transactional(readOnly = true)
    public List<FinancesResponseDTO> findAllFinances() {
        List<FinancesResponseDTO> financesResponseDTOList = financesRepository.findAll().stream().map(FinancesResponseDTO::new).toList();
        return financesResponseDTOList;
    }

    @Transactional(readOnly = true)
    public FinancesResponseDTO findById(String id) {
        Optional<Finances> finances = financesRepository.findById(id);
        if (finances.isEmpty()) throw new NoSuchElementException("Finances not found with id: " + id);
        else {
            return new FinancesResponseDTO(finances.get());
        }
    }

    @Transactional
    public FinancesResponseDTO createFinance(FinancesRequestDTO body, String email) {
        body.setUserEmail(userRepository.findUserByEmail(email));
        Finances newFinances = new Finances(body);
        newFinances = financesRepository.save(newFinances);
        return new FinancesResponseDTO(newFinances);
    }

    public void delete(String id) {
        try {
            financesRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Id não encontrado no banco de dados");
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Integrity Violation");
        }
    }

    @Transactional
    public FinancesResponseDTO update(String id, FinancesResponseDTO body) {
        try {
            Finances finances = financesRepository.getReferenceById(id);
            copyDtoToEntity(body, finances);
            finances = financesRepository.save(finances);
            return new FinancesResponseDTO(finances);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id não encontrado no banco de dados");
        }
    }

    private void copyDtoToEntity(FinancesResponseDTO dto, Finances entity) {
        entity.setDescription(dto.description());
        entity.setAmount(dto.amount());
        entity.setTypeFinances(dto.typeFinances());
        entity.setRepeat(dto.repeat());
        entity.setActive(dto.active());
    }
}
