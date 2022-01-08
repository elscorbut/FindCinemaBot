package com.bvg.service;

import com.bvg.commons.shared.exception.AnyServiceException;
import com.bvg.domain.DirectoryValue;
import com.bvg.domain.Movie;
import com.bvg.repository.IDirectoryValueRepository;
import com.bvg.repository.IMovieRepository;
import com.bvg.type.EDirectory;
import com.bvg.type.EType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class KpService {

    private static final String KP_HOST = "https://www.kinopoisk.ru";
    private static final int KP_USER_ID = 3218694;
    private static final int ITEMS_PER_PAGE = 200;
    private static final String KP_WISH_LIST_URL = "https://www.kinopoisk.ru/user/" + KP_USER_ID + "/movies/list/type/3575/sort/default/vector/desc/perpage/" + ITEMS_PER_PAGE + "/page/%s/#list";
    private static final String KP_IMAGE_URL = "https://st.kp.yandex.net/images/sm_film/%s.jpg";
    private static final String PATH_TO_CHROMEDRIVER = "D:/Progr projects/TelegramBot/src/main/resources/driver/chromedriver.exe";

    @Autowired
    IMovieRepository movieRepository;

    @Autowired
    IDirectoryValueRepository directoryValueRepository;

    @Autowired
    DirectoryService directoryService;

    @Autowired
    @Qualifier("disableRedirectRestTemplate")
    RestTemplate disableRedirectRestTemplate;

    public void updateKpWishList() throws InterruptedException {
        WebDriver driver = getWebDriver();
        Map<Long, Movie> currentMap = movieRepository.findAll().stream().collect(Collectors.toMap(Movie::getId, m -> m));
        Map<Long, Movie> newMap = new HashMap<>();
        int i = 1;
        while (true) {
            try {
                driver.get(String.format(KP_WISH_LIST_URL, i));
            } catch (Exception e) {
                throw new AnyServiceException("Ошибка при получении страницы КП: " + e.getMessage(), e);
            }
            if (driver.getCurrentUrl().contains("await")) {
                driver.quit();
                System.out.println("Последняя страница");
                break;
            }
            Document doc = Jsoup.parse(driver.getPageSource());
            Elements wishList = doc.select("#itemList li");
            for (Element wishListItem : wishList) {
                Long movieId = getMovieId(wishListItem);
                Movie movie = movieRepository.findById(movieId).orElse(new Movie(movieId));
                copyFields(movie, wishListItem);
                newMap.put(movieId, movie);
            }
            System.out.println("страница:" + i);
            i++;
            // Случайный интервал между запросами (2-10 сек.)
            TimeUnit.SECONDS.sleep((long) (2 + Math.random() * 10));
        }
        for (Map.Entry<Long, Movie> item : currentMap.entrySet()) {
            if (!newMap.containsKey(item.getKey())) {
                item.getValue().setDeleted(true);
                movieRepository.saveAndFlush(item.getValue());
            }
        }
        movieRepository.saveAll(newMap.values());
    }

    private WebDriver getWebDriver() {
        System.setProperty("webdriver.chrome.driver", PATH_TO_CHROMEDRIVER);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200", "--ignore-certificate-errors", "--silent");
        return new ChromeDriver(options);
    }

    private void copyFields(Movie movie, Element wishListItem) {
        movie.setId(movie.getId());
        movie.setPosterId(posterExists(wishListItem) ? getPosterId(movie.getId()) : null);
        movie.setKpRating(getRating(wishListItem, "rating"));
        movie.setImdbRating(getRating(wishListItem, "imdb"));

        Elements wishListItemInfoBlock = wishListItem.select("div.info");
        movie.setType(getType(wishListItemInfoBlock));
        movie.setRusName(getRusName(wishListItemInfoBlock));
        movie.setActors(getActors(wishListItemInfoBlock));
        movie.setCountry(getCountry(wishListItemInfoBlock));
        movie.setDirector(getDirector(wishListItemInfoBlock));
        movie.setGenres(getGenres(wishListItemInfoBlock));
        movie.setKpLink(getKpLink(wishListItemInfoBlock));

        String cinemaInfo = wishListItemInfoBlock.select("span").first().text();
        movie.setEngName(getValueByPattern(cinemaInfo, Pattern.compile("(.+) \\(")));
        String year = getValueByPattern(cinemaInfo, Pattern.compile("\\((\\d+)\\)"));
        movie.setYear(year != null ? Integer.valueOf(year) : null);
        String duration = getValueByPattern(cinemaInfo, Pattern.compile("\\) (\\d+)"));
        movie.setDuration(duration != null ? Integer.valueOf(duration) : null);
    }

    private boolean posterExists(Element wishListItem) {

        return !wishListItem.select("div.poster img").attr("title").contains("no-poster");
    }

    /**
     * Получаем id постера, в дальнейшем будем отдавать на клиент нужный url или несколько url'ов
     * https://avatars.mds.yandex.net/get-kinopoisk-image/1898899/f485e241-0758-4a9f-a4eb-98e586de8a37/52 - small
     * https://avatars.mds.yandex.net/get-kinopoisk-image/1898899/f485e241-0758-4a9f-a4eb-98e586de8a37/300x450 - medium
     * https://avatars.mds.yandex.net/get-kinopoisk-image/1898899/f485e241-0758-4a9f-a4eb-98e586de8a37/x1000 - large
     */
    private String getPosterId(Long movieId) {
        String posterId = null;
        try {
            HttpHeaders header = new HttpHeaders();
            header.set(HttpHeaders.ACCEPT, "text/html,application/xhtml+xml");
            header.set(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36");
            HttpEntity<String> request = new HttpEntity<>(header);
            ResponseEntity<Object> response = this.disableRedirectRestTemplate.exchange(String.format(KP_IMAGE_URL, movieId), HttpMethod.GET, request, Object.class);
            String location =
                    response.getHeaders().getLocation() != null ? response.getHeaders().getLocation().toString() : null;
            if (location != null) {
                String[] splitUrl = location.split("/");
                posterId = splitUrl[splitUrl.length - 3] + "/" + splitUrl[splitUrl.length - 2];
            }

        }
        catch (Exception e) {
            throw new AnyServiceException("Ошибка при получении ID постера: " + e.getMessage(), e);
        }

        return posterId;
    }

    private Long getMovieId(Element wishListItem) {

        return Long.valueOf(wishListItem.select("div.playTrailer").attr("data-film-id"));
    }

    private DirectoryValue getType(Elements wishListItemInfoBlock) {

        if (getRusName(wishListItemInfoBlock).contains("сериал")) {
            return directoryValueRepository.findByKey(EType.SERIES.name());
        } else {
            return directoryValueRepository.findByKey(EType.FILM.name());
        }
    }

    private String getRusName(Elements wishListItemInfoBlock) {

        return wishListItemInfoBlock.select("a.name").text();
    }

    private List<DirectoryValue> getActors(Elements wishListItemInfoBlock) {

        List<DirectoryValue> actorList = new ArrayList<>();
        Set<String> actors = wishListItemInfoBlock.select("span a.lined").eachText().stream().filter(item -> !item.contains("...")).collect(Collectors.toSet());
        actors.forEach(actor -> actorList.add(directoryService.getDirectoryValue(EDirectory.ACTOR, actor, true)));

        return actorList;
    }

    private DirectoryValue getCountry(Elements wishListItemInfoBlock) {

        return directoryService.getDirectoryValue(EDirectory.COUNTRY, wishListItemInfoBlock.select("b").first().ownText().replaceAll("[.,]+", ""), false);
    }

    private DirectoryValue getDirector(Elements wishListItemInfoBlock) {

        return directoryService.getDirectoryValue(EDirectory.DIRECTOR, wishListItemInfoBlock.select("b").select("i").select("a").text(), true);
    }

    private List<DirectoryValue> getGenres(Elements wishListItemInfoBlock) {

        List<DirectoryValue> genreList = new ArrayList<>();
        Set<String> genres = Stream.of(wishListItemInfoBlock.select("span").get(1).text().replaceAll("[().]", "").split(", ")).collect(Collectors.toSet());
        genres.forEach(genre -> genreList.add(directoryService.getDirectoryValue(EDirectory.GENRE, genre, true)));

        return genreList;
    }

    private String getKpLink(Elements wishListItemInfoBlock) {

        return KP_HOST + wishListItemInfoBlock.select("a.name").attr("href");
    }

    private Double getRating(Element wishListItem, String sourceName) {

        String ratingStr = wishListItem.select(String.format("div.%s b", sourceName)).text();

        return !StringUtils.isEmpty(ratingStr) ? (double) Math.round(Double.parseDouble(ratingStr) * 10) / 10 : null;
    }

    private String getValueByPattern(String cinemaInfo, Pattern pattern) {
        String value = null;
        Matcher valueMatcher = pattern.matcher(cinemaInfo);
        if (valueMatcher.find()) {
            value = valueMatcher.group(1);
        }
        return value;
    }
}
