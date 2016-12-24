package com.crewcloud.apps.crewnotice.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tunglam on 12/23/16.
 */

public class BaseResponse<D> {

    @SerializedName("d")
    public D data;

    public D getData() {
        return data;
    }
}
