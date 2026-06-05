package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.User;
import com.davidlima.task_organization_api.dto.user.UserRequestDTO;
import com.davidlima.task_organization_api.dto.user.UserResponseDTO;

public interface IUserMapper {

    User toUser (UserRequestDTO dto);

    UserResponseDTO toUserResponseDTO(User user);
}
