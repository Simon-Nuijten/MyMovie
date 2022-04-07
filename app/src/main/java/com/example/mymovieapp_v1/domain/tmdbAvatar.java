package com.example.mymovieapp_v1.domain;

public class tmdbAvatar {
    private String avatar_path;

    public tmdbAvatar(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }
}
