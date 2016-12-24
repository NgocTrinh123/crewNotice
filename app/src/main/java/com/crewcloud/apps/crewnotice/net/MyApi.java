package com.crewcloud.apps.crewnotice.net;

import com.crewcloud.apps.crewnotice.base.BaseResponse;
import com.crewcloud.apps.crewnotice.data.LeftMenu;
import com.crewcloud.apps.crewnotice.data.NoticeDetail;
import com.crewcloud.apps.crewnotice.dtos.Notice;
import com.crewcloud.apps.crewnotice.response.MenuResponse;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by mb on 3/30/16.
 */
public interface MyApi {

    @FormUrlEncoded
    @POST("/UI/WebService/WebServiceCenter.asmx/Login_v2")
    Observable<JSONObject> login(@FieldMap Map<String, String> params);

    @POST("/UI/MobileNotice/NoticeService.asmx/GetDivisions")
    Observable<BaseResponse<MenuResponse<List<LeftMenu>>>> getLeftMenu(@Body BodyRequest param);

    @POST("UI/MobileNotice/NoticeService.asmx/GetListOfNotices")
    Observable<BaseResponse<MenuResponse<List<Notice>>>> getListNotice(@Body BodyRequest param);

    @POST("UI/MobileNotice/NoticeService.asmx/GetListOfNotices")
    Observable<BaseResponse<MenuResponse<NoticeDetail>>> getDetailNotice(@Body BodyRequest param);
}