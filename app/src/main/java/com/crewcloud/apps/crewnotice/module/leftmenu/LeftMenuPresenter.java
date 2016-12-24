package com.crewcloud.apps.crewnotice.module.leftmenu;

import com.crewcloud.apps.crewnotice.base.BaseView;
import com.crewcloud.apps.crewnotice.data.LeftMenu;

import java.util.List;

/**
 * Created by tunglam on 12/23/16.
 */

public interface LeftMenuPresenter {
    interface view extends BaseView {
        void onGetMenuSuccess(List<LeftMenu> leftMenu);

        void onError(String message);
    }

    interface presenter {
        void getLeftMenu();
    }

}
