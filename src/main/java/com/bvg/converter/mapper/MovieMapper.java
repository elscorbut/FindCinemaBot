package com.bvg.converter.mapper;

import com.bvg.domain.Movie;
import com.bvg.dto.MovieDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    @Mappings(value = {
            @Mapping(target = "posterLinks", ignore = true),
            @Mapping(target = "actors", ignore = true),
            @Mapping(target = "country", ignore = true),
            @Mapping(target = "director", ignore = true),
            @Mapping(target = "genres", ignore = true),
            @Mapping(target = "type", ignore = true)})
    MovieDto entityToDto(Movie entity);
}
