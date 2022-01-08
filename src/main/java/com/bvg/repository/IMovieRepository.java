package com.bvg.repository;

import com.bvg.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Long>, QuerydslPredicateExecutor<Movie> {

}
