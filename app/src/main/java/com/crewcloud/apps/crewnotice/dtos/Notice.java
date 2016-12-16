package com.crewcloud.apps.crewnotice.dtos;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tunglam on 12/15/16.
 */

public class Notice {

    @SerializedName("id")
    private String id;

    @SerializedName("content")
    private String content;

    @SerializedName("number_comment")
    private String number_comment;

    @SerializedName("time")
    private String time;

    @SerializedName("author")
    private String author;

    public Notice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNumber_comment() {
        return number_comment;
    }

    public void setNumber_comment(String number_comment) {
        this.number_comment = number_comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
