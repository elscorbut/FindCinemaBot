package com.bvg.service;

import com.bvg.converter.MovieConverter;
import com.bvg.domain.Movie;
import com.bvg.dto.MovieDto;
import com.bvg.repository.IMovieRepository;
import com.bvg.specification.ISpecificationQbePredicate;
import com.bvg.type.MovieQbe;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService {

    private final IMovieRepository movieRepository;
    private final ISpecificationQbePredicate<MovieQbe> specification;
    private final MovieConverter movieConverter;

    public List<MovieDto> filter(MovieQbe qbe, Pageable pageable) {

        Predicate predicate = specification.predicate(qbe);
        Page<Movie> movies = movieRepository.findAll(predicate, pageable);

        return movies.stream().map(movieConverter::entityToDto).collect(Collectors.toList());
    }
}
