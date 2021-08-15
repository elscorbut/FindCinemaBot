package com.bvg.dao;

import com.bvg.commons.server.dao.JpaDao;
import com.bvg.domain.TmdbWatchlist;
import com.bvg.dto.TMDBWatchListDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Transactional
public class TmdbWatchlistDao extends JpaDao<TmdbWatchlist> {

    @Override
    public Class<TmdbWatchlist> getEntityClass() {
        return TmdbWatchlist.class;
    }

    /**
     * Обновление списка фильмов, пришедших после запроса watchlist с реусурса tmdb
     * Если id пришедшего фильма имеется в таблице tmdb_watchlist, то обновляем значения в таблице пришедшими
     * значениями из TmdbWatchlistFilmDto
     * Если приходит новый фильм, то добавляем его в таблицу tmdb_watchlist
     */
    //@formatter:off
    public void updateTmdbWatchlist(List<TMDBWatchListDto.TMDBWatchListFilmDto> tmdbWatchListDtos) {
        List<Long> tmdbWatchListDtoIds = tmdbWatchListDtos.stream().map(TMDBWatchListDto.TMDBWatchListFilmDto::getId).collect(Collectors.toList());
        Map<Long, TmdbWatchlist> tmdbWatchListMap = selectAll().stream().collect(Collectors.toMap(TmdbWatchlist::getId, y -> y));
        tmdbWatchListMap
                .keySet()
                .stream()
                .filter(tmdbWatchListId -> !tmdbWatchListDtoIds.contains(tmdbWatchListId))
                .map(tmdbWatchListMap::get)
                .forEach(entity -> entity.setDeleted(true));
        for (TMDBWatchListDto.TMDBWatchListFilmDto tmdbWatchListDto : tmdbWatchListDtos) {
            if (tmdbWatchListMap.containsKey(tmdbWatchListDto.getId())) {
                TmdbWatchlist entity = tmdbWatchListMap.get(tmdbWatchListDto.getId());
                entity.setAdult(tmdbWatchListDto.isAdult());
                entity.setBackdropPath(tmdbWatchListDto.getBackdropPath());
                entity.setOriginalLanguage(tmdbWatchListDto.getOriginalLanguage());
                entity.setOriginalTitle(tmdbWatchListDto.getOriginalTitle());
                entity.setOverview(tmdbWatchListDto.getOverview());
                entity.setPopularity(tmdbWatchListDto.getPopularity());
                entity.setPosterPath(tmdbWatchListDto.getPosterPath());
                entity.setReleaseDate(tmdbWatchListDto.getReleaseDate());
                entity.setTitle(tmdbWatchListDto.getTitle());
                entity.setVideo(tmdbWatchListDto.isVideo());
                entity.setVoteAverage(tmdbWatchListDto.getVoteAverage());
                entity.setVoteCount(tmdbWatchListDto.getVoteCount());
                entity.setDeleted(false);
            }
            else {
                persist(new TmdbWatchlist(
                        tmdbWatchListDto.getId(),
                        tmdbWatchListDto.getPopularity(),
                        tmdbWatchListDto.getVoteCount(),
                        tmdbWatchListDto.isVideo(),
                        tmdbWatchListDto.getPosterPath(),
                        tmdbWatchListDto.isAdult(),
                        tmdbWatchListDto.getBackdropPath(),
                        tmdbWatchListDto.getOriginalLanguage(),
                        tmdbWatchListDto.getOriginalTitle(),
                        tmdbWatchListDto.getTitle(),
                        tmdbWatchListDto.getVoteAverage(),
                        tmdbWatchListDto.getOverview(),
                        tmdbWatchListDto.getReleaseDate()));
            }
        }
    }
    //@formatter:on
}
