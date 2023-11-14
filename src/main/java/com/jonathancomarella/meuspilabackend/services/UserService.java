package com.jonathancomarella.meuspilabackend.services;

import com.jonathancomarella.meuspilabackend.domain.user.RegisterDTO;
import com.jonathancomarella.meuspilabackend.domain.user.User;
import com.jonathancomarella.meuspilabackend.domain.user.UserResponseDTO;
import com.jonathancomarella.meuspilabackend.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDetails findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean createUser(RegisterDTO body) {
        if (findByEmail(body.email()) != null) return false;

        String encryptedPassword = new BCryptPasswordEncoder().encode(body.password());
        User newUser = new User(body.email(), encryptedPassword, body.firstname(), body.lastname(), body.role());
        newUser = userRepository.save(newUser);
        System.out.println(newUser);
        return true;
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return new UserResponseDTO(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAll() {
        return userRepository.findAll().stream().map(UserResponseDTO::new).toList();
    }

    @Transactional
    public UserResponseDTO update(String email, UserResponseDTO body) {
        try {
            User user = userRepository.findUserByEmail(email);
            copyDtoToEntity(body, user);
            user = userRepository.save(user);
            return new UserResponseDTO(user);
        } catch (EntityNotFoundException e) {
            throw new EntityNotFoundException("Id do usuario não encontrado no banco de dados");
        }
    }

    private void copyDtoToEntity(UserResponseDTO dto, User entity) {
        entity.setEmail(dto.email());
        entity.setFirstname(dto.firstname());
        entity.setLastname(dto.lastname());
        entity.setRole(dto.role());
    }

    @Transactional
    public void deleteById(String email) {
        try {
            userRepository.deleteByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Id não encontrado no banco de dados");
        }
        catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Integrity Violation");
        }
    }
}
