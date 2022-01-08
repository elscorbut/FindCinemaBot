package com.bvg.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EPoster {

    SMALL(EPoster.GET_IMAGE_PATH + "52"),
    MEDIUM(EPoster.GET_IMAGE_PATH + "300x450"),
    LARGE(EPoster.GET_IMAGE_PATH + "x1000");

    private static final String GET_IMAGE_PATH = "https://avatars.mds.yandex.net/get-kinopoisk-image/%s/";
    public String link;
}
