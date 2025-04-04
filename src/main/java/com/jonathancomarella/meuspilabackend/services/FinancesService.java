package com.jonathancomarella.meuspilabackend.services;

import com.jonathancomarella.meuspilabackend.MeuspilaBackendApplication;
import com.jonathancomarella.meuspilabackend.domain.Finances;
import com.jonathancomarella.meuspilabackend.domain.dto.request.FinancesRequestDTO;
import com.jonathancomarella.meuspilabackend.domain.dto.response.FinancesResponseDTO;
import com.jonathancomarella.meuspilabackend.repositories.FinancesRepository;
import com.jonathancomarella.meuspilabackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(MeuspilaBackendApplication.class);

    @Autowired
    private FinancesRepository financesRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<FinancesResponseDTO> findAllFinancesByEmail(String email) {
        logger.info(email + " - Buscando todas as finances");
        List<FinancesResponseDTO> financesResponseDTOList = financesRepository.findDistinctByUserEmail_Email(email).stream().map(FinancesResponseDTO::new).toList();
        logger.info(email + " - Todas finances retornadas com sucesso!");
        return financesResponseDTOList;
    }

    @Transactional(readOnly = true)
    public List<FinancesResponseDTO> findAllFinances() {
        logger.info("Buscando todas finances");
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
        logger.info(email + " - Criando uma nova finance");
        body.setUserEmail(userRepository.findUserByEmail(email));
        Finances newFinances = new Finances(body);
        newFinances = financesRepository.save(newFinances);
        logger.info(email + " - Finance criada com sucesso");
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
        entity.setStatus(dto.status());
        entity.setRepeat(dto.repeat());
        entity.setActive(dto.active());
    }
}
