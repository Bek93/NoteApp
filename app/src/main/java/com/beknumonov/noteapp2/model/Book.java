package com.beknumonov.noteapp2.model;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("page_count")
    private String pageCount;

    @SerializedName("image")
    private String image;

    @SerializedName("created_at")
    private String createdAt;

    public Book() {
    }

    public Book(String title, String description, String pageCount, String image) {
        this.title = title;
        this.description = description;
        this.pageCount = pageCount;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
