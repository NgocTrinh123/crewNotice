package com.crewcloud.apps.crewnotice.module.noticedetail;

import com.crewcloud.apps.crewnotice.base.BaseView;
import com.crewcloud.apps.crewnotice.data.NoticeDetail;

/**
 * Created by tunglam on 12/24/16.
 */

public interface NoticeDetailPresenter  {
    interface view extends BaseView{
        void onGetDetailSuccess(NoticeDetail noticeDetail);

        void onError(String message);
    }

    interface presenter{
        void getNoticeDetail(int noticeNo);
    }
}
