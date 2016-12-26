package com.crewcloud.apps.crewnotice.module.notice;

import com.crewcloud.apps.crewnotice.base.BaseView;
import com.crewcloud.apps.crewnotice.dtos.Notice;

import java.util.List;

/**
 * Created by tunglam on 12/24/16.
 */

public interface NoticePresenter {
    interface view extends BaseView {
        void onGetNoticeSuccess(List<Notice> list);

        void onError(String message);
    }

    interface presenter {
        void getNotice(String search, int divisionNo, boolean isImportant, int anchorNoticeNo);
    }
}
