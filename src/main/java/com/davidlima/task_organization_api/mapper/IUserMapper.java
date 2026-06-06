package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.User;
import com.davidlima.task_organization_api.dto.user.UserRequestDTO;
import com.davidlima.task_organization_api.dto.user.UserResponseDTO;

public interface IUserMapper {

    default User toUser(UserRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return User.builder()
                .email(dto.email())
                .password(dto.password())
                .active(true)
                .build();
    }

    default UserResponseDTO toUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDTO(
                user.getId(),
                MapperUtils.personId(user.getPerson()),
                user.getEmail(),
                user.getActive(),
                MapperUtils.roleIds(user.getRoles())
        );
    }
}
