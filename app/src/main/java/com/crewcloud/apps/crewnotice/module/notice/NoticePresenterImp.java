package com.crewcloud.apps.crewnotice.module.notice;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.base.BasePresenter;
import com.crewcloud.apps.crewnotice.base.BaseResponse;
import com.crewcloud.apps.crewnotice.base.ResponseListener;
import com.crewcloud.apps.crewnotice.dtos.ErrorDto;
import com.crewcloud.apps.crewnotice.dtos.Notice;
import com.crewcloud.apps.crewnotice.loginv2.Statics;
import com.crewcloud.apps.crewnotice.net.APIService;
import com.crewcloud.apps.crewnotice.net.BodyRequest;
import com.crewcloud.apps.crewnotice.response.MenuResponse;
import com.crewcloud.apps.crewnotice.util.TimeUtils;
import com.crewcloud.apps.crewnotice.util.Util;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tunglam on 12/24/16.
 */

public class NoticePresenterImp extends BasePresenter<NoticePresenter.view> implements NoticePresenter.presenter {

    private Activity activity;

    public NoticePresenterImp(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void getNotice(String search, int divisionNo, boolean isImportant, int anchorNoticeNo) {
        if (isViewAttached()) {
            String sessionId = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId();
            String languageCode = Util.getPhoneLanguage();
            String timeZoneOffset = String.valueOf(TimeUtils.getTimezoneOffsetInMinutes());
            BodyRequest bodyRequest = new BodyRequest(timeZoneOffset, languageCode, sessionId);
            bodyRequest.setDivisionNo(divisionNo);
            bodyRequest.setImportantOnly(isImportant);
            bodyRequest.setSearchText(search);
            bodyRequest.setCountOfArticles(Statics.COUNT_OF_ARTICLES);
            bodyRequest.setAnchorNoticeNo(anchorNoticeNo);
            APIService.getInstance()
                    .getListNotice(bodyRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ResponseListener<BaseResponse<MenuResponse<List<Notice>>>>() {
                        @Override
                        public void onSuccess(BaseResponse<MenuResponse<List<Notice>>> result) {
                            getView().onGetNoticeSuccess(result.getData().getList());

                        }

                        @Override
                        public void onError(@NonNull ErrorDto messageResponse) {
                            getView().onError(messageResponse.message);
                        }
                    });
        }
    }
}
