package com.bvg.specification;

import com.bvg.converter.DirectoryValueConverter;
import com.bvg.domain.QMovie;
import com.bvg.repository.IDirectoryValueRepository;
import com.bvg.type.MovieQbe;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class MovieQbeSpecification implements ISpecificationQbePredicate<MovieQbe> {

    private static final QMovie MOVIE = QMovie.movie;
    private final IDirectoryValueRepository directoryValueRepository;
    private final DirectoryValueConverter directoryValueConverter;

    public Predicate predicate(MovieQbe qbe) {
        List<Predicate> expressions = new ArrayList<>(1);

        if (qbe != null) {
            if (qbe.getType() != null) {
                expressions.add(MOVIE.type.eq(directoryValueConverter.dtoToEntity(qbe.getType())));
            }
            if (qbe.getRusName() != null) {
                expressions.add(MOVIE.rusName.containsIgnoreCase(qbe.getRusName()));
            }
            if (qbe.getEngName() != null) {
                expressions.add(MOVIE.engName.containsIgnoreCase(qbe.getEngName()));
            }
            if (!CollectionUtils.isEmpty(qbe.getYears())) {
                expressions.add(MOVIE.year.in(qbe.getYears()));
            }
            if (!CollectionUtils.isEmpty(qbe.getActors())) {
                expressions.add(MOVIE.actors.any().in(directoryValueConverter.dtosToEntities(qbe.getActors())));
            }
            if (!CollectionUtils.isEmpty(qbe.getCountries())) {
                expressions.add(MOVIE.country.in(directoryValueConverter.dtosToEntities(qbe.getCountries())));
            }
            if (!CollectionUtils.isEmpty(qbe.getDirectors())) {
                expressions.add(MOVIE.director.in(directoryValueConverter.dtosToEntities(qbe.getDirectors())));
            }
            if (!CollectionUtils.isEmpty(qbe.getGenres())) {
                expressions.add(MOVIE.genres.any().in(directoryValueConverter.dtosToEntities(qbe.getGenres())));
            }
            if (qbe.getKpRatingFrom() != null) {
                expressions.add(MOVIE.kpRating.goe(qbe.getKpRatingFrom()));
            }
            if (qbe.getKpRatingUntil() != null) {
                expressions.add(MOVIE.kpRating.loe(qbe.getKpRatingUntil()));
            }
            if (qbe.getImdbRatingFrom() != null) {
                expressions.add(MOVIE.kpRating.goe(qbe.getImdbRatingFrom()));
            }
            if (qbe.getImdbRatingUntil() != null) {
                expressions.add(MOVIE.kpRating.loe(qbe.getImdbRatingUntil()));
            }
            if (qbe.isDeleted()) {
                expressions.add(MOVIE.deleted.eq(qbe.isDeleted()));
            }
        }
        if (expressions.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }
        return ExpressionUtils.allOf(expressions);
    }
}
