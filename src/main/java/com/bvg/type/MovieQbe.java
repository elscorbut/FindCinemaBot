package com.bvg.type;

import com.bvg.dto.DirectoryValueDto;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MovieQbe {

    /** Рейтинг КП, с */
    private Double kpRatingFrom;

    /** Рейтинг КП, по */
    private Double kpRatingUntil;

    /** Рейтинг IMDB, с */
    private Double imdbRatingFrom;

    /** Рейтинг IMDB, по */
    private Double imdbRatingUntil;

    /** Название, на русском */
    private String rusName;

    /** Название, на английском */
    private String engName;

    /** Тип (фильм, сериал) */
    private DirectoryValueDto type;

    /** Годы */
    private List<Integer> years;

    /** Актеры */
    private List<DirectoryValueDto> actors = new ArrayList<>();

    /** Страны */
    private List<DirectoryValueDto> countries = new ArrayList<>();

    /** Режиссеры */
    private List<DirectoryValueDto> directors = new ArrayList<>();

    /** Жанры */
    private List<DirectoryValueDto> genres = new ArrayList<>();

    /** Удаленные */
    private boolean deleted;
}
