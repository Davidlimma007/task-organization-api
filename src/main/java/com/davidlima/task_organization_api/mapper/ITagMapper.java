package com.davidlima.task_organization_api.mapper;

import com.davidlima.task_organization_api.database.model.Tag;
import com.davidlima.task_organization_api.dto.tag.TagRequestDTO;
import com.davidlima.task_organization_api.dto.tag.TagResponseDTO;

public interface ITagMapper {

    Tag toTag(TagRequestDTO dto);

    TagResponseDTO toTagResponseDTO(Tag tag);
}
