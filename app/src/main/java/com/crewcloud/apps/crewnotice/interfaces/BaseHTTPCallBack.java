package com.crewcloud.apps.crewnotice.interfaces;


import com.crewcloud.apps.crewnotice.dtos.ErrorDto;

public interface BaseHTTPCallBack {
    void onHTTPSuccess();
    void onHTTPFail(ErrorDto errorDto);
}
