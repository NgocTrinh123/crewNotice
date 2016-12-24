package com.crewcloud.apps.crewnotice.module.noticedetail;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.base.BasePresenter;
import com.crewcloud.apps.crewnotice.base.BaseResponse;
import com.crewcloud.apps.crewnotice.base.ResponseListener;
import com.crewcloud.apps.crewnotice.data.NoticeDetail;
import com.crewcloud.apps.crewnotice.dtos.ErrorDto;
import com.crewcloud.apps.crewnotice.net.APIService;
import com.crewcloud.apps.crewnotice.net.BodyRequest;
import com.crewcloud.apps.crewnotice.response.MenuResponse;
import com.crewcloud.apps.crewnotice.util.TimeUtils;
import com.crewcloud.apps.crewnotice.util.Util;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tunglam on 12/24/16.
 */

public class NoticeDetailPresenterImp extends BasePresenter<NoticeDetailPresenter.view> implements NoticeDetailPresenter.presenter {
    private Activity activity;

    public NoticeDetailPresenterImp(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getNoticeDetail(int noticeNo) {
        if (isViewAttached()) {
            String sessionId = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId();
            String languageCode = Util.getPhoneLanguage();
            String timeZoneOffset = String.valueOf(TimeUtils.getTimezoneOffsetInMinutes());
            BodyRequest bodyRequest = new BodyRequest(timeZoneOffset, languageCode, sessionId);
            bodyRequest.setNoticeNo(noticeNo);

            APIService.getInstance().getDetailNotice(bodyRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseListener<BaseResponse<MenuResponse<NoticeDetail>>>() {
                        @Override
                        public void onSuccess(BaseResponse<MenuResponse<NoticeDetail>> result) {
                            getView().onGetDetailSuccess(result.getData().getList());
                        }

                        @Override
                        public void onError(@NonNull ErrorDto messageResponse) {
                            getView().onError(messageResponse.message);
                        }
                    });
        }
    }
}
