package com.davidlima.task_organization_api.service;

import com.davidlima.task_organization_api.database.model.Roles;
import com.davidlima.task_organization_api.database.model.User;
import com.davidlima.task_organization_api.database.repository.IRolesRepository;
import com.davidlima.task_organization_api.database.repository.IUserRepository;
import com.davidlima.task_organization_api.dto.user.UserRequestDTO;
import com.davidlima.task_organization_api.dto.user.UserResponseDTO;
import com.davidlima.task_organization_api.exception.BusinessRuleException;
import com.davidlima.task_organization_api.exception.ResourceNotFoundException;
import com.davidlima.task_organization_api.mapper.IUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final IUserRepository userRepository;
    private final IRolesRepository rolesRepository;
    private final IUserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO create(UserRequestDTO dto) {
        validateUniqueEmail(dto.email(), null);
        User user = userMapper.toUser(dto);
        user.setEmail(normalizeEmail(dto.email()));
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setActive(true);
        user.setRoles(new HashSet<>());
        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserResponseDTO findById(UUID id) {
        return userMapper.toUserResponseDTO(findEntityById(id));
    }

    public UserResponseDTO updateEmail(UUID id, String email) {
        User user = findEntityById(id);
        validateUniqueEmail(email, id);
        user.setEmail(normalizeEmail(email));
        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    public void updatePassword(UUID id, String password) {
        User user = findEntityById(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public UserResponseDTO activate(UUID id) {
        User user = findEntityById(id);
        user.setActive(true);
        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO deactivate(UUID id) {
        User user = findEntityById(id);
        user.setActive(false);
        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    public UserResponseDTO replaceRoles(UUID id, Set<UUID> rolesId) {
        User user = findEntityById(id);
        Set<Roles> roles = rolesId == null || rolesId.isEmpty()
                ? new HashSet<>()
                : new HashSet<>(rolesRepository.findAllById(rolesId));
        if (rolesId != null && roles.size() != rolesId.size()) {
            throw new ResourceNotFoundException("Um ou mais papéis informados não foram encontrados.");
        }
        user.setRoles(roles);
        return userMapper.toUserResponseDTO(userRepository.save(user));
    }

    public User findEntityById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
    }

    private void validateUniqueEmail(String email, UUID currentId) {
        userRepository.findByEmailIgnoreCase(normalizeEmail(email))
                .filter(user -> currentId == null || !user.getId().equals(currentId))
                .ifPresent(user -> {
                    throw new BusinessRuleException("Já existe um usuário cadastrado com este e-mail.");
                });
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }
}