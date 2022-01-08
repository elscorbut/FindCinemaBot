package com.bvg.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Фильмы пользователя из списка "Буду смотреть" на КП
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "T_MOVIE")
public class Movie implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "poster_id")
    private String posterId;

    @Column(name = "kp_rating")
    private Double kpRating;

    @Column(name = "imdb_rating")
    private Double imdbRating;

    @Column(name = "rus_name")
    private String rusName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_MOVIE_ACTORS", joinColumns = {@JoinColumn(name = "movie_id")}, inverseJoinColumns = {@JoinColumn(name = "actor_id")})
    private List<DirectoryValue> actors = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private DirectoryValue country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "director_id")
    private DirectoryValue director;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "T_MOVIE_GENRES", joinColumns = {@JoinColumn(name = "movie_id")}, inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<DirectoryValue> genres = new ArrayList<>();

    @Column(name = "kp_link")
    private String kpLink;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "year")
    private Integer year;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "deleted")
    private boolean deleted;

    // Тип: фильм, сериал
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private DirectoryValue type;

    public Movie(Long movieId) {
        this.id = movieId;
    }
}
