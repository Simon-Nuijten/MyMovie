package com.example.mymovieapp_v1.domain;

public class Review {
    private String author;
    private Author author_details;
    private String content;
    private String created_at;
    private String id;
    private String updated_at;
    private String url;

    public Review(String author, Author author_details, String content, String created_at, String id, String updated_at, String url) {
        this.author = author;
        this.author_details = author_details;
        this.content = content;
        this.created_at = created_at;
        this.id = id;
        this.updated_at = updated_at;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Author getAuthor_details() {
        return author_details;
    }

    public void setAuthor_details(Author author_details) {
        this.author_details = author_details;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
