package com.crewcloud.apps.crewnotice.module.login;

import android.text.TextUtils;

import com.crewcloud.apps.crewnotice.R;
import com.crewcloud.apps.crewnotice.base.BasePresenter;
import com.crewcloud.apps.crewnotice.loginv2.BaseActivity;
import com.crewcloud.apps.crewnotice.net.APIService;
import com.crewcloud.apps.crewnotice.util.Urls;
import com.crewcloud.apps.crewnotice.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tunglam on 12/17/16.
 */

public class LoginPresenterImp extends BasePresenter<LoginPresenter.view> implements LoginPresenter.presenter {

    private BaseActivity activity;

    public LoginPresenterImp(BaseActivity activity) {
        this.activity = activity;
    }


    @Override
    public void login(final String user_id, final String pass, final String company_domain, String serverSite) {

        final String url = serverSite + Urls.URL_GET_LOGIN;
        Map<String, String> params = new HashMap<>();
        params.put("languageCode", Util.getPhoneLanguage());
        params.put("timeZoneOffset", "" + Util.getTimeOffsetInMinute());
        params.put("companyDomain", company_domain);
        params.put("password", pass);
        params.put("userID", user_id);
        params.put("mobileOSVersion", "Android " + android.os.Build.VERSION.RELEASE);
        Observable<JSONObject> observable = APIService.getInstance().login(params);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().onLoginError(e.getMessage());
                    }

                    @Override
                    public void onNext(JSONObject loginResponse) {
                        Gson gson = new Gson();
//                        UserDto userDto = gson.fromJson(loginResponse, UserDto.class);
//                        userDto.prefs.setCurrentMobileSessionId(userDto.session);
//                        userDto.prefs.setCurrentUserIsAdmin(userDto.PermissionType);
//                        userDto.prefs.setCurrentCompanyNo(userDto.CompanyNo);
//                        userDto.prefs.setCurrentUserNo(userDto.Id);
//                        userDto.prefs.setCurrentUserID(userDto.userID);
//                        userDto.prefs.setUserAvatar(userDto.avatar);
//                        userDto.prefs.setCompanyName(userDto.NameCompany);
//                        userDto.prefs.setEmail(userDto.MailAddress);
//                        userDto.prefs.setFullName(userDto.FullName);
//                        userDto.prefs.setPass(pass);
//                        userDto.prefs.setUserID(user_id);
//                        userDto.prefs.setDomain(company_domain);
//                        getView().onLoginSuccess(userDto);
                    }
                });


//        WebServiceManager webServiceManager = new WebServiceManager();
//        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
//            @Override
//            public void onSuccess(String response) {
//                Util.printLogs("User info =" + response);
//                Gson gson = new Gson();
//                UserDto userDto = gson.fromJson(response, UserDto.class);
//                userDto.prefs.setCurrentMobileSessionId(userDto.session);
//                userDto.prefs.setCurrentUserIsAdmin(userDto.PermissionType);
//                userDto.prefs.setCurrentCompanyNo(userDto.CompanyNo);
//                userDto.prefs.setCurrentUserNo(userDto.Id);
//                userDto.prefs.setCurrentUserID(userDto.userID);
//                userDto.prefs.setUserAvatar(userDto.avatar);
//                userDto.prefs.setCompanyName(userDto.NameCompany);
//                userDto.prefs.setEmail(userDto.MailAddress);
//                userDto.prefs.setFullName(userDto.FullName);
//                userDto.prefs.setPass(password);
//                userDto.prefs.setUserID(userID);
//                userDto.prefs.setDomain(companyDomain);
//
//                baseHTTPCallBack.onHTTPSuccess();
//            }
//
//            @Override
//            public void onFailure(ErrorDto error) {
//                baseHTTPCallBack.onHTTPFail(error);
//            }
//        });
    }

    @Override
    public void validata(String server_site, String username, String password) {
        String result = "";

        if (TextUtils.isEmpty(server_site)) {
            result += activity.getString(R.string.string_server_site);
        }

        if (TextUtils.isEmpty(username)) {
            if (TextUtils.isEmpty(result)) {
                result += activity.getString(R.string.login_username);
            } else {
                result += ", " + activity.getString(R.string.login_username);
            }
        }

        if (TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(result)) {
                result += activity.getString(R.string.login_password);
            } else {
                result += ", " + activity.getString(R.string.login_password);
            }
        }


        if (!TextUtils.isEmpty(result)) {
            getView().onValidataError(result + " " + activity.getString(R.string.login_empty_input));
        } else {
            getView().onValidataSuccess(server_site, username, password);
        }
    }
}
