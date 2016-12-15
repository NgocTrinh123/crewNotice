package com.crewcloud.apps.crewnotice.dtos;

import com.google.gson.annotations.SerializedName;

public class ErrorDto {

    public boolean unAuthentication;

    @SerializedName("code")
    public int code = 1;

    @SerializedName("message")
    public String message = "";

    @Override
    public String toString() {
        return "ErrorDto{" +
                "unAuthentication=" + unAuthentication +
                ", code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
