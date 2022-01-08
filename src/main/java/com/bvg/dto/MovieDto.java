package com.bvg.dto;

import com.bvg.type.EPoster;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto implements Serializable {

    @Schema(description = "ID фильма")
    private Long id;

    @Schema(description = "Ссылки на постеры")
    private Map<EPoster, String> posterLinks = new HashMap<>();

    @Schema(description = "рейтинг КП")
    private Double kpRating;

    @Schema(description = "рейтинг IMDB")
    private Double imdbRating;

    @Schema(description = "Российское название")
    private String rusName;

    @Schema(description = "Список актеров")
    private List<DirectoryValueDto> actors = new ArrayList<>();

    @Schema(description = "Страна")
    private DirectoryValueDto country;

    @Schema(description = "Режиссер")
    private DirectoryValueDto director;

    @Schema(description = "Список жанров")
    private List<DirectoryValueDto> genres = new ArrayList<>();

    @Schema(description = "Ссылка на КП")
    private String kpLink;

    @Schema(description = "Английское название")
    private String engName;

    @Schema(description = "Год выхода фильма")
    private Integer year;

    @Schema(description = "Продолжительность, минуты")
    private Integer duration;

    @Schema(description = "Фильм удален")
    private boolean deleted;

    @Schema(description = "Тип (фильм, сериал)")
    private DirectoryValueDto type;
}
