package com.crewcloud.apps.crewnotice.response;

import com.crewcloud.apps.crewnotice.dtos.UserDto;


/**
 * Created by tunglam on 12/17/16.
 */

public class LoginResponse {

    private UserDto data;

    public LoginResponse() {
    }

    public UserDto getData() {
        return data;
    }

    public void setData(UserDto data) {
        this.data = data;
    }
}
