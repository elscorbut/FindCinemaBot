package com.bvg.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TMDB_WATCHLIST", schema = "tg_data")
public class TmdbWatchlist {

    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "popularity")
    private Double popularity;

    @Column(name = "vote_count")
    public Integer voteCount;

    @Column(name = "video")
    public boolean video;

    @Column(name = "poster_path")
    public String posterPath;

    @Column(name = "adult")
    public boolean adult;

    @Column(name = "backdrop_path")
    public String backdropPath;

    @Column(name = "original_language")
    public String originalLanguage;

    @Column(name = "original_title")
    public String originalTitle;

    @Column(name = "title")
    public String title;

    @Column(name = "vote_average")
    public Double voteAverage;

    @Column(name = "overview")
    public String overview;

    @Column(name = "release_date")
    public Date releaseDate;

    @Column(name = "deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    public boolean deleted = false;

    public TmdbWatchlist(Long id, Double popularity, Integer voteCount, boolean video, String posterPath, boolean adult,
            String backdropPath, String originalLanguage, String originalTitle, String title, Double voteAverage, String overview, Date releaseDate) {
        this.id = id;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.posterPath = posterPath;
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.title = title;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
}
