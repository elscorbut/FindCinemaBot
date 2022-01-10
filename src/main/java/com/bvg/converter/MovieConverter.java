package com.bvg.converter;

import com.bvg.converter.mapper.MovieMapper;
import com.bvg.domain.Movie;
import com.bvg.dto.MovieDto;
import com.bvg.type.EPoster;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieConverter {

    private final DirectoryValueConverter directoryValueConverter;

    public MovieDto entityToDto(Movie movie) {

        MovieDto response = MovieMapper.INSTANCE.entityToDto(movie);
        setPosterLinks(response, movie.getPosterId());
        response.setDirector(directoryValueConverter.entityToDto(movie.getDirector()));
        response.setCountry(directoryValueConverter.entityToDto(movie.getCountry()));
        response.setType(directoryValueConverter.entityToDto(movie.getType()));
        response.setActors(directoryValueConverter.entitiesToDtos(movie.getActors()));
        response.setGenres(directoryValueConverter.entitiesToDtos(movie.getGenres()));

        return response;
    }

    private void setPosterLinks (MovieDto response, String posterId) {

        for (EPoster poster : EPoster.values()) {
            response.getPosterLinks().put(poster, posterId != null ? String.format(poster.getLink(), posterId) : null);
        }
    }
}
