package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.dto.person.PersonRequestDTO;
import com.davidlima.task_organization_api.dto.person.PersonResponseDTO;

public interface IPersonMapper {

    Person toPerson(PersonRequestDTO dto);

    PersonResponseDTO toPersonResponseDTO(Person person);
}
