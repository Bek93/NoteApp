package com.beknumonov.noteapp2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class News implements Serializable {

    @SerializedName("id")
    private int id;


    @SerializedName("title")
    private String title;

    @SerializedName("source")
    private String source;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("image_url")
    private String image_url;

    public News(int id, String title, String source, String author, String content, String image_url) {
        this.id = id;
        this.title = title;
        this.source = source;
        this.author = author;
        this.content = content;
        this.image_url = image_url;
    }

    public News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
