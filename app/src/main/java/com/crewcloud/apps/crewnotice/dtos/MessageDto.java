package com.crewcloud.apps.crewnotice.dtos;

import com.google.gson.annotations.SerializedName;

public class MessageDto {
    @SerializedName("Message")
    private String Message;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}