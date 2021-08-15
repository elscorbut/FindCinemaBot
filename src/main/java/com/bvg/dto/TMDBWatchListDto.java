package com.bvg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TMDBWatchListDto {
    @JsonProperty(value = "page")
    private Long page;
    @JsonProperty("total_results")
    private Long totalResults;
    @JsonProperty("total_pages")
    private Long totalPages;
    @JsonProperty("results")
    private List<TMDBWatchListFilmDto> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TMDBWatchListFilmDto {
        @JsonProperty(value = "popularity")
        private Double popularity;
        @JsonProperty(value = "vote_count")
        private Integer voteCount;
        @JsonProperty(value = "video")
        private boolean video;
        @JsonProperty(value = "poster_path")
        private String posterPath;
        @JsonProperty(value = "id")
        private Long id;
        @JsonProperty(value = "adult")
        private boolean adult;
        @JsonProperty(value = "backdrop_path")
        private String backdropPath;
        @JsonProperty(value = "original_language")
        private String originalLanguage;
        @JsonProperty(value = "original_title")
        private String originalTitle;
        @JsonProperty(value = "genre_ids")
        private List<Long> genreIds;
        @JsonProperty(value = "title")
        private String title;
        @JsonProperty(value = "vote_average")
        private Double voteAverage;
        @JsonProperty(value = "overview")
        private String overview;
        @JsonProperty(value = "release_date")
        private Date releaseDate;
    }
}
