package com.crewcloud.apps.crewnotice.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tunglam on 12/17/16.
 */

public class Reply {

    @SerializedName("id")
    private String id;

    @SerializedName("comment")
    private String comment;

    @SerializedName("name")
    private String name;

    @SerializedName("avatar")
    private String avatar;

    public Reply(String id, String comment, String name, String avatar) {
        this.id = id;
        this.comment = comment;
        this.name = name;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
