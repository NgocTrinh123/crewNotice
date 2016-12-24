package com.crewcloud.apps.crewnotice.module.leftmenu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.base.BasePresenter;
import com.crewcloud.apps.crewnotice.base.BaseResponse;
import com.crewcloud.apps.crewnotice.base.ResponseListener;
import com.crewcloud.apps.crewnotice.data.LeftMenu;
import com.crewcloud.apps.crewnotice.dtos.ErrorDto;
import com.crewcloud.apps.crewnotice.net.APIService;
import com.crewcloud.apps.crewnotice.net.BodyRequest;
import com.crewcloud.apps.crewnotice.response.MenuResponse;
import com.crewcloud.apps.crewnotice.util.TimeUtils;
import com.crewcloud.apps.crewnotice.util.Util;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tunglam on 12/23/16.
 */

public class LeftMenuPresenterImp extends BasePresenter<LeftMenuPresenter.view> implements LeftMenuPresenter.presenter {

    Activity activity;

    public LeftMenuPresenterImp(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getLeftMenu() {
        if (isViewAttached()) {
            String sessionId = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId();
            String languageCode = Util.getPhoneLanguage();
            String timeZoneOffset = String.valueOf(TimeUtils.getTimezoneOffsetInMinutes());
            BodyRequest bodyRequest = new BodyRequest(timeZoneOffset, languageCode, sessionId);

            APIService.getInstance()
                    .getLeftMenu(bodyRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseListener<BaseResponse<MenuResponse<List<LeftMenu>>>>() {

                        @Override
                        public void onSuccess(BaseResponse<MenuResponse<List<LeftMenu>>> result) {
                            Log.d("SUCCESS", result.toString());
                            getView().onGetMenuSuccess(result.getData().getList());
                        }

                        @Override
                        public void onError(@NonNull ErrorDto messageResponse) {
                            getView().onError(messageResponse.message);
                        }
                    });
        }

//        if (isViewAttached()) {
//            String sessionId = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId();
//            String url = "http://www.crewcloud.net/UI/MobileNotice/NoticeService.asmx/GetDivisions";
//            Map<String, Object> params = new HashMap<>();
//            params.put("sessionId", sessionId);
//            params.put("languageCode", Util.getPhoneLanguage());
//            params.put("timeZoneOffset", String.valueOf(TimeUtils.getTimezoneOffsetInMinutes()));
//            WebServiceManager webServiceManager = new WebServiceManager();
//            webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
//                @Override
//                public void onSuccess(String response) {
//                    Log.d("response", response);
//                }
//
//                @Override
//                public void onFailure(ErrorDto error) {
//                    Log.e(TAG, "onFailure");
//                }
//            });
//        }

    }
}
