package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.dto.person.PersonRequestDTO;
import com.davidlima.task_organization_api.dto.person.PersonResponseDTO;

public interface IPersonMapper {

    default Person toPerson(PersonRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Person.builder()
                .user(MapperUtils.userReference(dto.userId()))
                .name(dto.name())
                .surname(dto.surname())
                .build();
    }

    default PersonResponseDTO toPersonResponseDTO(Person person) {
        if (person == null) {
            return null;
        }
        return new PersonResponseDTO(
                person.getId(),
                MapperUtils.userId(person.getUser()),
                person.getName(),
                person.getSurname()
        );
    }
}
