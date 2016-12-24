package com.crewcloud.apps.crewnotice.response;

import com.crewcloud.apps.crewnotice.dtos.ErrorDto;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tunglam on 12/23/16.
 */

public class MenuResponse<D> {

    @SerializedName("__type")
    private String type;

    @SerializedName("success")
    private boolean success;

    @SerializedName("data")
    private D list;

    @SerializedName("error")
    private ErrorDto errorDto;

    public MenuResponse() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public D getList() {
        return list;
    }

    public void setList(D list) {
        this.list = list;
    }

    public ErrorDto getErrorDto() {
        return errorDto;
    }

    public void setErrorDto(ErrorDto errorDto) {
        this.errorDto = errorDto;
    }
}
