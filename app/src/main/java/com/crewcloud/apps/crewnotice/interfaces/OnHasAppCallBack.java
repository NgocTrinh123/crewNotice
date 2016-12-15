package com.crewcloud.apps.crewnotice.interfaces;


import com.crewcloud.apps.crewnotice.dtos.ErrorDto;

public interface OnHasAppCallBack {
    void hasApp();
    void noHas(ErrorDto dto);
}
