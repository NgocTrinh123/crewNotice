package com.crewcloud.apps.crewnotice.interfaces;


import com.crewcloud.apps.crewnotice.dtos.ErrorDto;

public interface BaseHTTPCallBackWithString {
    void onHTTPSuccess(String message);
    void onHTTPFail(ErrorDto errorDto);
}
