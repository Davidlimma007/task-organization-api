package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Tag;
import com.davidlima.task_organization_api.dto.tag.TagRequestDTO;
import com.davidlima.task_organization_api.dto.tag.TagResponseDTO;

public interface ITagMapper {

    default Tag toTag(TagRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Tag.builder()
                .name(dto.name())
                .author(MapperUtils.personReference(dto.authorId()))
                .build();
    }

    default TagResponseDTO toTagResponseDTO(Tag tag) {
        if (tag == null) {
            return null;
        }
        return new TagResponseDTO(
                tag.getId(),
                tag.getName(),
                MapperUtils.personId(tag.getAuthor()),
                MapperUtils.personFullName(tag.getAuthor()),
                tag.getDateCreated()
        );
    }
}
