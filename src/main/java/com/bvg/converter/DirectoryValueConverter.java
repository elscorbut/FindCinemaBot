package com.bvg.converter;

import com.bvg.domain.DirectoryValue;
import com.bvg.dto.DirectoryValueDto;
import com.bvg.repository.IDirectoryValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryValueConverter {

    @Autowired
    protected IDirectoryValueRepository directoryValueRepository;

    public DirectoryValueDto entityToDto(DirectoryValue entity) {
        if (entity == null) {
            return null;
        }
        //@formatter:off
        return DirectoryValueDto.builder()
                .id(entity.getId())
                .key(entity.getKey())
                .value(entity.getValue())
                .parentId(entity.getParentId())
                .directoryId(entity.getDirectoryId())
                .shortValue(entity.getShortValue())
                .hidden(entity.isHidden())
                .priority(entity.getPriority())
                .build();
        //@formatter:on
    }

    public DirectoryValue dtoToEntity(DirectoryValueDto dto) {
        if (dto == null) {
            return null;
        }

        return directoryValueRepository.getOne(dto.getId());
    }

    public List<DirectoryValue> dtosToEntities(List<DirectoryValueDto> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return new ArrayList<>();
        }
        List<DirectoryValue> list = new ArrayList<>();
        for (DirectoryValueDto dto : dtos) {
            list.add(dtoToEntity(dto));
        }

        return list;
    }

    public List<DirectoryValueDto> entitiesToDtos(List<DirectoryValue> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return new ArrayList<>();
        }
        List<DirectoryValueDto> list = new ArrayList<>();
        for (DirectoryValue entity : entities) {
            list.add(entityToDto(entity));
        }

        return list;
    }
}
