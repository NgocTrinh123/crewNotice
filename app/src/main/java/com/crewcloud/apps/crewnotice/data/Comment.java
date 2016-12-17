package com.crewcloud.apps.crewnotice.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tunglam on 12/16/16.
 */

public class Comment {
    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("time")
    private String time;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("comment")
    private String comment;

    @SerializedName("reply")
    private List<Reply> lstReply;

    public Comment() {
    }

    public Comment(String id, String author, String time, String avatar, String comment) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.avatar = avatar;
        this.comment = comment;
    }

    public Comment(String id, String author, String time, String avatar, String comment, List<Reply> lstReply) {
        this.id = id;
        this.author = author;
        this.time = time;
        this.avatar = avatar;
        this.comment = comment;
        this.lstReply = lstReply;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Reply> getLstReply() {
        return lstReply;
    }

    public void setLstReply(List<Reply> lstReply) {
        this.lstReply = lstReply;
    }
}

