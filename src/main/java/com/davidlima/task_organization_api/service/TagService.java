package com.davidlima.task_organization_api.service;

import com.davidlima.task_organization_api.database.model.Person;
import com.davidlima.task_organization_api.database.model.Tag;
import com.davidlima.task_organization_api.database.repository.ITagRepository;
import com.davidlima.task_organization_api.dto.tag.TagRequestDTO;
import com.davidlima.task_organization_api.dto.tag.TagResponseDTO;
import com.davidlima.task_organization_api.exception.BusinessRuleException;
import com.davidlima.task_organization_api.exception.ResourceNotFoundException;
import com.davidlima.task_organization_api.mapper.ITagMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final ITagRepository tagRepository;
    private final PersonService personService;
    private final ITagMapper tagMapper;

    public TagResponseDTO create(TagRequestDTO dto) {
        validateUniqueName(dto.name(), null);
        Person author = personService.findEntityById(dto.authorId());
        Tag tag = tagMapper.toTag(dto);
        tag.setName(normalizeName(dto.name()));
        tag.setAuthor(author);
        return tagMapper.toTagResponseDTO(tagRepository.save(tag));
    }

    @Transactional(readOnly = true)
    public List<TagResponseDTO> findAll() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toTagResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TagResponseDTO findById(UUID id) {
        return tagMapper.toTagResponseDTO(findEntityById(id));
    }

    public TagResponseDTO update(UUID id, TagRequestDTO dto) {
        Tag tag = findEntityById(id);
        validateUniqueName(dto.name(), id);
        Person author = personService.findEntityById(dto.authorId());
        tag.setName(normalizeName(dto.name()));
        tag.setAuthor(author);
        return tagMapper.toTagResponseDTO(tagRepository.save(tag));
    }

    public void delete(UUID id) {
        Tag tag = findEntityById(id);
        tagRepository.delete(tag);
    }

    public List<Tag> findEntitiesByIds(List<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return Collections.emptyList();
        }
        List<Tag> tags = tagRepository.findByIdIn(ids);
        if (tags.size() != ids.stream().distinct().count()) {
            throw new ResourceNotFoundException("Uma ou mais tags informadas não foram encontradas.");
        }
        return tags;
    }

    public Tag findEntityById(UUID id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag não encontrada."));
    }

    private void validateUniqueName(String name, UUID currentId) {
        tagRepository.findByNameIgnoreCase(normalizeName(name))
                .filter(tag -> currentId == null || !tag.getId().equals(currentId))
                .ifPresent(tag -> {
                    throw new BusinessRuleException("Já existe uma tag cadastrada com este nome.");
                });
    }

    private String normalizeName(String name) {
        return name == null ? null : name.trim().toLowerCase();
    }
}
