package com.crewcloud.apps.crewnotice.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.crewcloud.apps.crewnotice.CrewCloudApplication;

public class PreferenceUtilitiesBackup {
    private SharedPreferences mPreferences;

    private final String KEY_CURRENT_SERVICE_DOMAIN = "currentServiceDomain";
    private final String KEY_CURRENT_COMPANY_DOMAIN = "currentCompanyDomain";
    private final String KEY_CURRENT_COMPANY_NO = "currentCompanyNo";
    private final String KEY_CURRENT_MOBILE_SESSION_ID = "currentMobileSessionId";
    private final String KEY_CURRENT_USER_ID = "currentUserID";

    public PreferenceUtilitiesBackup() {
        mPreferences = CrewCloudApplication.getInstance().getApplicationContext().getSharedPreferences("CrewNotice_Prefs", Context.MODE_PRIVATE);
    }

    public void setCurrentServiceDomain(String domain) {
        mPreferences.edit().putString(KEY_CURRENT_SERVICE_DOMAIN, domain).apply();
    }

    public String getCurrentServiceDomain() {
        return mPreferences.getString(KEY_CURRENT_SERVICE_DOMAIN, "");
    }

    public void setCurrentCompanyDomain(String domain) {
        mPreferences.edit().putString(KEY_CURRENT_COMPANY_DOMAIN, domain).apply();
    }

    public String getCurrentCompanyDomain() {
        return mPreferences.getString(KEY_CURRENT_COMPANY_DOMAIN, "");
    }

    public void setCurrentCompanyNo(int companyNo) {
        mPreferences.edit().putInt(KEY_CURRENT_COMPANY_NO, companyNo).apply();
    }

    public int getCurrentCompanyNo() {
        return mPreferences.getInt(KEY_CURRENT_COMPANY_NO, 0);
    }

    public void setCurrentMobileSessionId(String sessionId) {
        mPreferences.edit().putString(KEY_CURRENT_MOBILE_SESSION_ID, sessionId).apply();
    }

    public String getCurrentMobileSessionId() {
        return mPreferences.getString(KEY_CURRENT_MOBILE_SESSION_ID, "");
    }

    // ----------------------------------------------------------------------------------------------

    public void setCurrentUserID(String userID) {
        mPreferences.edit().putString(KEY_CURRENT_USER_ID, userID).apply();
    }

    public String getCurrentUserID() {
        return mPreferences.getString(KEY_CURRENT_USER_ID, "");
    }
}