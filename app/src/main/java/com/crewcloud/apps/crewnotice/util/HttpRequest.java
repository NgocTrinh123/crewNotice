package com.crewcloud.apps.crewnotice.util;

import android.util.Log;

import com.android.volley.Request;
import com.crewcloud.apps.crewnotice.Constants;
import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.dtos.ErrorDto;
import com.crewcloud.apps.crewnotice.dtos.MessageDto;
import com.crewcloud.apps.crewnotice.dtos.UserDto;
import com.crewcloud.apps.crewnotice.interfaces.BaseHTTPCallBack;
import com.crewcloud.apps.crewnotice.interfaces.BaseHTTPCallBackWithString;
import com.crewcloud.apps.crewnotice.interfaces.OnAutoLoginCallBack;
import com.crewcloud.apps.crewnotice.interfaces.OnHasAppCallBack;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.crewcloud.apps.crewnotice.loginv2.Statics.TAG;

public class HttpRequest {

    private static HttpRequest mInstance;
    private static String root_link;

    public static HttpRequest getInstance() {
        if (null == mInstance) {
            mInstance = new HttpRequest();
        }
        root_link = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentCompanyDomain();

        return mInstance;
    }

    public void login(final BaseHTTPCallBack baseHTTPCallBack, final String userID, final String password, final String companyDomain, String server_link) {
        final String url = server_link + Urls.URL_GET_LOGIN;
        Map<String, String> params = new HashMap<>();
        params.put("languageCode", Util.getPhoneLanguage());
        params.put("timeZoneOffset", "" + Util.getTimeOffsetInMinute());
        params.put("companyDomain", companyDomain);
        params.put("password", password);
        params.put("userID", userID);
        params.put("mobileOSVersion", "Android " + android.os.Build.VERSION.RELEASE);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Util.printLogs("User info =" + response);
                Gson gson = new Gson();
                UserDto userDto = gson.fromJson(response, UserDto.class);
                userDto.prefs.setCurrentMobileSessionId(userDto.session);
                userDto.prefs.setCurrentUserIsAdmin(userDto.PermissionType);
                userDto.prefs.setCurrentCompanyNo(userDto.CompanyNo);
                userDto.prefs.setCurrentUserNo(userDto.Id);
                userDto.prefs.setCurrentUserID(userDto.userID);
                userDto.prefs.setUserAvatar(userDto.avatar);
                userDto.prefs.setCompanyName(userDto.NameCompany);
                userDto.prefs.setEmail(userDto.MailAddress);
                userDto.prefs.setFullName(userDto.FullName);
                userDto.prefs.setPass(password);
                userDto.prefs.setUserID(userID);
                userDto.prefs.setDomain(companyDomain);

                baseHTTPCallBack.onHTTPSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                baseHTTPCallBack.onHTTPFail(error);
            }
        });
    }

    public void signUp(final BaseHTTPCallBackWithString baseHTTPCallBack, final String email) {
        final String url = Urls.URL_SIGN_UP;
        Map<String, String> params = new HashMap<>();
        params.put("languageCode", Util.getPhoneLanguage());
        params.put("mailAddress", "" + email);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                MessageDto messageDto = gson.fromJson(response, MessageDto.class);

                if (baseHTTPCallBack != null && messageDto != null) {
                    String message = messageDto.getMessage();
                    baseHTTPCallBack.onHTTPSuccess(message);
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (baseHTTPCallBack != null) {
                    baseHTTPCallBack.onHTTPFail(error);
                }
            }
        });
    }

    public void checkLogin(final BaseHTTPCallBack baseHTTPCallBack) {
        final String url = root_link + Urls.URL_CHECK_SESSION;
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", "" + CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("languageCode", Util.getPhoneLanguage());
        params.put("timeZoneOffset", "" + Util.getTimeOffsetInMinute());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                UserDto userDto = gson.fromJson(response, UserDto.class);
                userDto.prefs.setCurrentMobileSessionId(userDto.session);
                userDto.prefs.setCurrentUserIsAdmin(userDto.PermissionType);
                userDto.prefs.setCurrentCompanyNo(userDto.CompanyNo);
                userDto.prefs.setCurrentUserNo(userDto.Id);
                userDto.prefs.setCurrentUserID(userDto.userID);
                //UserDBHelper.addUser(userDto);
                if (baseHTTPCallBack != null) {
                    baseHTTPCallBack.onHTTPSuccess();
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                if (baseHTTPCallBack != null) {
                    baseHTTPCallBack.onHTTPFail(error);
                }
            }
        });
    }

    public void checkApplication(final OnHasAppCallBack callBack) {
        final String url = root_link + Urls.URL_HAS_APPLICATION;
        Map<String, String> params = new HashMap<>();
        String projectCode = "WorkingTime";

        params.put("projectCode", projectCode);
        params.put("languageCode", Util.getPhoneLanguage());
        params.put("timeZoneOffset", "" + Util.getTimeOffsetInMinute());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    if (callBack != null) {
                        if (json.getBoolean("HasApplication")) {
                            callBack.hasApp();
                        } else {
                            ErrorDto errorDto = new ErrorDto();
                            errorDto.message = json.getString("Message");
                            callBack.noHas(errorDto);
                        }
                    }
                } catch (Exception e) {
                    callBack.noHas(new ErrorDto());
                }
            }

            @Override
            public void onFailure(ErrorDto error) {
                callBack.noHas(error);
            }
        });
    }


    public void logout(final BaseHTTPCallBack baseHTTPCallBack) {
        final String url = root_link + Urls.URL_LOG_OUT;
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", "" + CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {

                // Clear Login
                CrewCloudApplication.getInstance().getPreferenceUtilities().setCurrentServiceDomain("");
                CrewCloudApplication.getInstance().getPreferenceUtilities().setCurrentUserID("");

                baseHTTPCallBack.onHTTPSuccess();
            }

            @Override
            public void onFailure(ErrorDto error) {
                baseHTTPCallBack.onHTTPFail(error);
            }
        });
    }


    /**
     * AUTO LOGIN
     */
    public void AutoLogin(String companyDomain, String userID, String server_link, final OnAutoLoginCallBack callBack) {
        final String url = server_link + Urls.URL_AUTO_LOGIN;
        Map<String, String> params = new HashMap<>();
        params.put("languageCode", Util.getPhoneLanguage());
        params.put("timeZoneOffset", "" + Util.getTimeOffsetInMinute());
        params.put("companyDomain", companyDomain);
        params.put("userID", userID);
        params.put("mobileOSVersion", "Android " + android.os.Build.VERSION.RELEASE);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Util.printLogs("User info =" + response);
                Gson gson = new Gson();
                UserDto userDto = gson.fromJson(response, UserDto.class);
                userDto.prefs.setCurrentMobileSessionId(userDto.session);
                userDto.prefs.setCurrentUserIsAdmin(userDto.PermissionType);
                userDto.prefs.setCurrentCompanyNo(userDto.CompanyNo);
                userDto.prefs.setCurrentUserNo(userDto.Id);
                userDto.prefs.setUserAvatar(userDto.avatar);
                userDto.prefs.setCompanyName(userDto.NameCompany);
                userDto.prefs.setEmail(userDto.MailAddress);
                userDto.prefs.setFullName(userDto.FullName);
                //UserDBHelper.addUser(userDto);

                callBack.OnAutoLoginSuccess(response);
            }

            @Override
            public void onFailure(ErrorDto error) {
                callBack.OnAutoLoginFail(error);
            }
        });
    }


    //----------------------------------------------- Notification ---------------------------------------------------------------

    public static void insertAndroidDevice(final BaseHTTPCallBack callBack, String regid, String json) {
        final String url = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentServiceDomain() + Constants.URL_INSERT_ANDROID_DEVICE;

        Map<String, String> params = new HashMap<>();
        params.put("sessionId", "" + CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", "" + Util.getTimezoneOffsetInMinutes());
        params.put("deviceID", regid);
        params.put("osVersion", "Android " + android.os.Build.VERSION.RELEASE);
        params.put("languageCode", Util.getPhoneLanguage());
        params.put("notificationOptions", json);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                callBack.onHTTPSuccess();
//                Log.e(TAG,"response:"+response);
            }

            @Override
            public void onFailure(ErrorDto error) {

//                baseHTTPCallBack.onHTTPFail(error);
            }
        });
    }

    public static void updateAndroidDevice(String regid, String json) {
        final String url = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentServiceDomain() + Constants.URL_UPDATE_ANDROID_DEVICE;

        Map<String, String> params = new HashMap<>();
        params.put("sessionId", "" + CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", "" + Util.getTimezoneOffsetInMinutes());
        params.put("deviceID", regid);
        params.put("languageCode", Util.getPhoneLanguage());
        params.put("notificationOptions", json);
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {

                Log.e("Update API", "Update:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {

//                baseHTTPCallBack.onHTTPFail(error);
            }
        });
    }

    public static void deleteAndroidDevice(final BaseHTTPCallBack callBack) {
        final String url = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentServiceDomain() + Constants.URL_DELETE_ANDROID_DEVICE;
        Map<String, String> params = new HashMap<>();
        params.put("sessionId", "" + CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", "" + Util.getTimezoneOffsetInMinutes());
        params.put("languageCode", Util.getPhoneLanguage());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {
                Log.e("Delete", " :" + response);
                callBack.onHTTPSuccess();
//                Log.e(TAG,"response:"+response);
            }

            @Override
            public void onFailure(ErrorDto error) {

//                baseHTTPCallBack.onHTTPFail(error);
            }
        });
    }

    public static void updateTimeZone(String regid) {
        final String url = CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentServiceDomain() + Constants.URL_UPDATE_TIMEZONE_ANDROID_DEVICE;

        Map<String, String> params = new HashMap<>();
        params.put("sessionId", "" + CrewCloudApplication.getInstance().getPreferenceUtilities().getCurrentMobileSessionId());
        params.put("timeZoneOffset", "" + Util.getTimezoneOffsetInMinutes());
        params.put("deviceID", regid);
        params.put("languageCode", Util.getPhoneLanguage());
        WebServiceManager webServiceManager = new WebServiceManager();
        webServiceManager.doJsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new WebServiceManager.RequestListener<String>() {
            @Override
            public void onSuccess(String response) {

                Log.e(TAG, "Update TimeZone response:" + response);
            }

            @Override
            public void onFailure(ErrorDto error) {

//                baseHTTPCallBack.onHTTPFail(error);
            }
        });
    }
}