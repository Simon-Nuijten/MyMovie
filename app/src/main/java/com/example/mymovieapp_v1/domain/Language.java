package com.example.mymovieapp_v1.domain;

public class Language {
    private final String iso_639_1;
    private final String english_name;
    private final String name;

    public Language(String iso_639_1, String english_name, String name) {
        this.iso_639_1 = iso_639_1;
        this.english_name = english_name;
        this.name = name;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public String getEnglish_name() {
        return english_name;
    }

    public String getName() {
        return name;
    }
}
