package com.davidlima.task_organization_api.service;

import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.User;
import com.davidlima.task_organization_api.database.repository.IPersonRepository;
import com.davidlima.task_organization_api.database.repository.IUserRepository;
import com.davidlima.task_organization_api.dto.person.PersonRequestDTO;
import com.davidlima.task_organization_api.dto.person.PersonResponseDTO;
import com.davidlima.task_organization_api.exception.BusinessRuleException;
import com.davidlima.task_organization_api.exception.ResourceNotFoundException;
import com.davidlima.task_organization_api.mapper.IPersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final IPersonRepository personRepository;
    private final IUserRepository userRepository;
    private final IPersonMapper personMapper;

    public PersonResponseDTO create(PersonRequestDTO dto, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
        if (personRepository.existsByUserId(userId)) {
            throw new BusinessRuleException("O usuário informado já possui uma pessoa vinculada.");
        }

        Person person = personMapper.toPerson(dto);
        person.setUser(user);
        return personMapper.toPersonResponseDTO(personRepository.save(person));
    }

    @Transactional(readOnly = true)
    public List<PersonResponseDTO> findAll() {
        return personRepository.findAll().stream()
                .map(personMapper::toPersonResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PersonResponseDTO findById(UUID id) {
        return personMapper.toPersonResponseDTO(findEntityById(id));
    }

    public PersonResponseDTO update(UUID id, PersonRequestDTO dto) {
        Person person = findEntityById(id);
        person.setName(dto.name());
        person.setSurname(dto.surname());
        return personMapper.toPersonResponseDTO(personRepository.save(person));
    }

    public void delete(UUID id) {
        Person person = findEntityById(id);
        personRepository.delete(person);
    }

    public Person findEntityById(UUID id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pessoa não encontrada."));
    }
}
