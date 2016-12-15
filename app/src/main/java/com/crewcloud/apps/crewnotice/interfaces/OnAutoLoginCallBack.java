package com.crewcloud.apps.crewnotice.interfaces;


import com.crewcloud.apps.crewnotice.dtos.ErrorDto;

/**
 * Created by Dat on 7/27/2016.
 */
public interface OnAutoLoginCallBack {
    void OnAutoLoginSuccess(String response);
    void OnAutoLoginFail(ErrorDto dto);
}
