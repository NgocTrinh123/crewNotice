package com.crewcloud.apps.crewnotice.module.login;

import com.crewcloud.apps.crewnotice.base.BaseView;
import com.crewcloud.apps.crewnotice.dtos.UserDto;

/**
 * Created by tunglam on 12/17/16.
 */

public interface LoginPresenter {
    interface view extends BaseView {
        void onLoginSuccess(UserDto userDto);

        void onLoginError(String message);

        void onValidataSuccess(String server_site, String user_id, String pass);

        void onValidataError(String message);
    }

    interface presenter {
        void login(String user_id, String pass, String company_domain, String serverSite);

        void validata(String server_site, String user_id, String pass);
    }
}
